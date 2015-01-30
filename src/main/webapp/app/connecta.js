define([
    'angular',
    // Módulos Angular Connecta
    'connecta.portal',
    'connecta.collector',
    'connecta.speaknow',
    'connecta.presenter',
    'connecta.maps',
    // Dependências principais
    'angular-route',
    'angular-resource',
    'angular-ui-bootstrap',
    'angular-ng-table',
    'bower_components/angular-animate/angular-animate.min',
    'bower_components/angular-cookies/angular-cookies.min',
    'bower_components/angular-touch/angular-touch.min',
    'bower_components/angular-i18n/angular-locale_pt-br'
], function (angular, portal, collector, speaknow, presenter, maps) {

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
        'ui.bootstrap'
    ]);

    // TODO Organizar as dependências e extrair código pro config do Angular
    connecta.appBootstrap = {
        run: function (config) {
            connecta.constant('applications', config.applications);
            //Configuração da aplicaçao
            connecta.config(function ($routeProvider, $controllerProvider, $compileProvider, $provide, speaknowRoutes) {
                connecta.conf = config;

                // save references to the providers
                var lazy = {
                    controller: $controllerProvider.register,
                    directive: $compileProvider.directive,
                    factory: $provide.factory,
                    service: $provide.service
                };
                
                connecta.lazy = lazy;
                portal.lazy = lazy;
                collector.lazy = lazy;
                speaknow.lazy = lazy;
                presenter.lazy = lazy;
                maps.lazy = lazy;

                // Páginas das aplicações
                var routePage = {
                    template: ' ',
                    resolve: {
                        load: [
                            '$route',
                            'pageService',
                            function ($route, pageService) {
                                var application = $route.current.params.app;
                                var module = $route.current.params.module;
                                var controller = $route.current.params.controller;
                                var id = $route.current.params.id;
                                return pageService.resolvePage(application, module, controller, id);
                            }
                        ]
                    }
                };

                // Listagem padronizada de itens
                var routeSearch = {
                    template: ' ',
                    resolve: {
                        load: [
                            '$route',
                            'searchService',
                            function ($route, searchService) {
                                var application = $route.current.params.app;
                                var item = $route.current.params.item;
                                var child = $route.current.params.child;

                                if (!($route.current.originalPath.match(/^\/search-a/))) {
                                    return searchService.resolveEmbedded(application, item, child);
                                } else {
                                    return searchService.resolvePage(application, item, child);
                                }
                            }
                        ]
                    }
                };

                /**
                 * Usado pra resolver os caminhos das controllers
                 * @param {type} route
                 * @param {type} url
                 * @returns {undefined}
                 */
                var registerResolver = function(route, url){
                    if ( route.controllerUrl ) {
                        if ( !route.resolve ) {
                            route.resolve = {};
                        }

                        route.resolve.load = function ($q, $rootScope) {
                            var deferred = $q.defer();
                            require([route.controllerUrl], function(){
                                deferred.resolve();
                                $rootScope.$apply();
                            });
                            return deferred.promise;
                        };
                        
                    }
                    
                    $routeProvider.when(url, route);
                };
                
                angular.forEach(speaknowRoutes, registerResolver);
                
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
            });

            //start
            require([
                'domReady!',
                'portal/controllers/main'
            ], function (doc) {
                angular.bootstrap(doc, [connecta.name]);
            });
        },
        init: function () {
            require([
                'json!./../application',
                'domReady!',
                'portal/services/applications',
                'portal/services/pages',
                'portal/services/layout',
                'portal/services/search'
            ], function (applications) {
                var config = {
                    applications: applications
                };

                connecta.appBootstrap.run(config);
            });
        }
    };

    return connecta;
});