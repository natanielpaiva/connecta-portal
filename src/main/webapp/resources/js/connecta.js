define([
    'angular',
    // Módulos Angular Connecta
    'connecta.portal',
    'connecta.collector',
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
], function(angular) {
    var connecta = angular.module('connecta', [
        'connecta.portal',
        'connecta.collector',
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
        run: function(config) {
            //Configuração da aplicaçao
            connecta.config([
                '$routeProvider',
                '$controllerProvider',
                '$compileProvider',
                '$provide',
                function($routeProvider, $controllerProvider, $compileProvider, $provide) {
                    connecta.conf = config;

                    // save references to the providers
                    connecta.lazy = {
                        controller: $controllerProvider.register,
                        directive: $compileProvider.directive,
                        factory: $provide.factory,
                        service: $provide.service
                    };

                    // Páginas das aplicações
                    var routePage = {
                        template: ' ',
                        resolve: {
                            load: [
                                '$route',
                                'pageService',
                                function($route, pageService) {
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
                                function($route, searchService) {
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

                    // @todo: Procurar forma de angular trazer parametro opcional (id)
                    // por enquanto é um gato
                    $routeProvider
                            .when('/search/:app/:item', routeSearch)
                            .when('/search/:app/:item/:child', routeSearch)
                            .when('/search-a/:app/:item', routeSearch)
                            .when('/search-a/:app/:item/:child', routeSearch)
                            .when('/:app', routePage)
                            .when('/:app/:module', routePage)
                            .when('/:app/:module/:controller', routePage)
                            .when('/:app/:module/:controller/:id', routePage);
                }]);

            //start
            require([
                'domReady!',
                'portal/controllers/main'
            ], function(doc) {
                angular.bootstrap(doc, [connecta.name]);
            });
        },
        init: function() {
            require([
                'json!./../../services/rest/applications',
                'domReady!',
                'portal/services/applications',
                'portal/services/pages',
                'portal/services/layout',
                'portal/services/search'
            ], function(config) {
                // Adiciona o path das aplicações na configuração do require, isso permite
                // requisições simplificadas
                var applicationsPaths = {};
                $.each(config.applications, function(i, item) {
                    if (item.host && item.host !== '') {
                        var baseHost = [
                            item.host.replace(/\/+$/g, ''),
                            'resources',
                            'js'
                        ].join('/');
                        applicationsPaths[item.name] = baseHost;

                        //path para o json de configuração da aplicaçao
                        applicationsPaths[(item.name + "-config")] = [
                            item.host,
                            //@todo: recuperar daconfig
                            'services/rest/config.json'
                        ].join('/');
                    }
                });
                require.config({
                    paths: applicationsPaths
                });

                connecta.appBootstrap.run(config);
            });
        }
    };
    
    return connecta;
});