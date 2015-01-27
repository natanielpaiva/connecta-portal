define([
    'angular'
], function (angular) {
    var speaknow = angular.module('connecta.speaknow', []);

    speaknow.constant('speaknowRoutes', {
        '/speaknow/': {
            controller: 'ContactListController',
            controllerUrl: 'speaknow/controllers/contact-list',
            templateUrl: 'app/speaknow/partials/contact-list.html'
        }
    });

    return speaknow;
});