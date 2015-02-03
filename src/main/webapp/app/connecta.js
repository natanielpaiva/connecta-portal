define([
    'angular',
    'jquery',
    // Módulos Angular Connecta
    'connecta.portal',
    'connecta.collector',
    'connecta.speaknow',
    'connecta.presenter',
    'connecta.maps',
    // Configuração dos módulos
    'json!./../application',
    // Dependências principais
    'angular-route',
    'angular-resource',
    'angular-ui-bootstrap',
    'angular-ng-table',
    'bower_components/angular-animate/angular-animate.min',
    'bower_components/angular-cookies/angular-cookies.min',
    'bower_components/angular-touch/angular-touch.min',
    'bower_components/angular-i18n/angular-locale_pt-br',
    'bower_components/angular-translate/angular-translate.min',
    'bower_components/angular-translate-loader-partial/angular-translate-loader-partial.min',
    'bower_components/angular-translate-storage-cookie/angular-translate-storage-cookie.min'
], function (angular, $, portal, collector, speaknow, presenter, maps, applications) {

    var connecta = angular.module('connecta', [
        'connecta.portal',
        'connecta.collector',
        'connecta.speaknow',
        'connecta.presenter',
        'connecta.maps',
        'ngRoute',
        'ngResource',
        'ngCookies',
        'ngAnimate',
        'ngTouch',
        'ngLocale',
        'ngTable',
        'ui.bootstrap',
        'pascalprecht.translate'
    ]);

    // Configuração do backend dos módulos
    connecta.constant('applications', applications);

    /**
     * Salva a referência dos providers em todos os módulos para poder
     * registrar componentes por lazy loading
     * 
     * @param {type} $controllerProvider
     * @param {type} $compileProvider
     * @param {type} $provide
     * @param {type} $filterProvider
     * @returns {undefined}
     */
    function configureLazyProviders($controllerProvider, $compileProvider, $provide, $filterProvider) {
        var lazy = {
            controller: $controllerProvider.register,
            directive: $compileProvider.directive,
            constant: $provide.constant,
            decorator: $provide.decorator,
            factory: $provide.factory,
            provider: $provide.provider,
            service: $provide.service,
            value: $provide.value,
            filter: $filterProvider.register
        };

        connecta.lazy = lazy;
        portal.lazy = lazy;
        collector.lazy = lazy;
        speaknow.lazy = lazy;
        presenter.lazy = lazy;
        maps.lazy = lazy;
    }
    
    /**
     * Configura as traduções da aplicação
     * @param {type} $translateProvider
     * @param {type} navigator
     * @returns {undefined}
     */
    function configureTranslations($translateProvider, navigator){
        $translateProvider.useLoader('$translatePartialLoader', {
            urlTemplate: 'app/{part}/translate/{lang}.json'
        });

        $translateProvider.determinePreferredLanguage(function(){
            if ( navigator.userLanguage || navigator.language ) {
                return navigator.userLanguage || navigator.language;
            }
            
            return 'en-us';
        });
        
        $translateProvider.fallbackLanguage('en-us');
        
        $translateProvider.useCookieStorage();
    }
    
    /**
     * Mescla todas as rotas passadas e retorna o objeto final das rotas
     * @returns {object}
     */
    function buildRoutes() {
        var finalRouteObject = {};
        angular.forEach(arguments, function (module) {
            $.extend(true, finalRouteObject, module._routes);
        });
        return finalRouteObject;
    }
    
    /**
     * Configura as rotas da aplicação
     * @param {type} $routeProvider
     * @returns {undefined}
     */
    function configureRoutes($routeProvider) {
        var allRoutes = buildRoutes(portal, collector, speaknow, presenter, maps);
        
        angular.forEach(allRoutes, function (route, url) {
            if (route.controllerUrl) {
                if (!route.resolve) {
                    route.resolve = {};
                }

                if (!route.resolve.load) {
                    route.resolve.load = function ($q, $rootScope) {
                        var deferred = $q.defer();
                        require([route.controllerUrl], function () {
                            deferred.resolve();
                            $rootScope.$apply();
                        });
                        return deferred.promise;
                    };
                }
            }

            $routeProvider.when(url, route);
        });
    }

    connecta.config(function ($controllerProvider, $compileProvider, $provide, $filterProvider, $translateProvider, $routeProvider) {

        configureLazyProviders($controllerProvider, $compileProvider, $provide, $filterProvider);
        configureTranslations($translateProvider, window.navigator);
        configureRoutes($routeProvider);
        
    });

    /**
     * Inicia as dependências principais do módulo do Portal e inicia a aplicação
     * 
     * FIXME Devia apenas chamar as diretivas e cada uma delas declarar suas dependências
     * 
     * @param {type} doc
     * @returns {undefined}
     */
    require([
        'domReady!',
        'portal/layout/controller/main',
        'portal/layout/controller/home',
        'portal/layout/service/applications',
        'portal/layout/service/pages',
        'portal/layout/service/layout',
        'portal/layout/service/search'
    ], function (doc) {
        angular.bootstrap(doc, [connecta.name]);
    });

    return connecta;
});

// CÓDIGO DE ROTAS ANTIGO
//                // Páginas das aplicações
//                var routePage = {
//                    template: ' ',
//                    resolve: {
//                        load: [
//                            '$route',
//                            'pageService',
//                            function ($route, pageService) {
//                                var application = $route.current.params.app;
//                                var module = $route.current.params.module;
//                                var controller = $route.current.params.controller;
//                                var id = $route.current.params.id;
//                                return pageService.resolvePage(application, module, controller, id);
//                            }
//                        ]
//                    }
//                };
//
//                // Listagem padronizada de itens
//                var routeSearch = {
//                    template: ' ',
//                    resolve: {
//                        load: [
//                            '$route',
//                            'searchService',
//                            function ($route, searchService) {
//                                var application = $route.current.params.app;
//                                var item = $route.current.params.item;
//                                var child = $route.current.params.child;
//
//                                if (!($route.current.originalPath.match(/^\/search-a/))) {
//                                    return searchService.resolveEmbedded(application, item, child);
//                                } else {
//                                    return searchService.resolvePage(application, item, child);
//                                }
//                            }
//                        ]
//                    }
//                };

// @todo: Procurar forma de angular trazer parametro opcional (id)
// por enquanto é um gato
//                $routeProvider
//                        .when('/search/:app/:item', routeSearch)
//                        .when('/search/:app/:item/:child', routeSearch)
//                        .when('/search-a/:app/:item', routeSearch)
//                        .when('/search-a/:app/:item/:child', routeSearch)
//                        .when('/:app', routePage)
//                        .when('/:app/:module', routePage)
//                        .when('/:app/:module/:controller', routePage)
//                        .when('/:app/:module/:controller/:id', routePage);