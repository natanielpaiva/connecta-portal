define([
    'angular'
], function (angular) {
    var portal = angular.module('connecta.portal', [
        'pascalprecht.translate'
    ]);
    
    portal.config(function($translatePartialLoaderProvider){
        $translatePartialLoaderProvider.addPart('portal/layout');
    });

    portal._routes = {
        '/': {
            controller: 'HomeController',
            controllerUrl: 'portal/layout/controller/home',
            templateUrl: 'app/portal/layout/template/home.html'
        }
    };

    return portal;
});