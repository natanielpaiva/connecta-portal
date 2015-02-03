define([
    'angular'
], function (angular) {
    var speaknow = angular.module('connecta.speaknow', []);
    
    speaknow.config(function($translatePartialLoaderProvider){
        $translatePartialLoaderProvider.addPart('speaknow/contact');
    });

    speaknow._routes = {
        '/speaknow/contact': {
            controller: 'ContactListController',
            controllerUrl: 'speaknow/contact/controller/contact-list',
            templateUrl: 'app/speaknow/contact/template/contact-list.html'
        }
    };

    return speaknow;
});