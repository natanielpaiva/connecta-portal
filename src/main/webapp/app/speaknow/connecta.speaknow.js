define([
    'angular'
], function (angular) {
    var speaknow = angular.module('connecta.speaknow', []);

    speaknow.config(function ($translatePartialLoaderProvider) {
        $translatePartialLoaderProvider.addPart('speaknow/contact');
        $translatePartialLoaderProvider.addPart('speaknow/interaction');
    });

    speaknow._routes = {
        /**
         * URL CONTACTS
         */
        '/speaknow/contact': {
            controller: 'ContactListController',
            controllerUrl: 'speaknow/contact/controller/contact-list',
            templateUrl: 'app/speaknow/contact/template/contact-list.html'
        },
        '/speaknow/contact/new':{
            controller: 'ContactFormController',
            controllerUrl: 'speaknow/contact/controller/contact-form',
            templateUrl: 'app/speaknow/contact/template/contact-form.html'
        },
        /**
         * URL INTERACTIONS
         */
        '/speaknow/interaction': {
            controller: 'InteractionListController',
            controllerUrl: 'speaknow/interaction/controller/interaction-list',
            templateUrl: 'app/speaknow/interaction/template/interaction-list.html'
        },
        '/speaknow/interaction/new':{
            controller: 'InteractionFormController',
            controllerUrl: 'speaknow/interaction/controller/interaction-form',
            templateUrl: 'app/speaknow/interaction/template/interaction-form.html'
        }
    };

    speaknow.run(function (applications) {
        var appSpeaknow = applications.filter(function (app) {
            return app.name === 'speaknow';
        }).pop();

        speaknow.lazy.value('speaknowResources', {
            contact: appSpeaknow.host + '/contact',
            interaction: appSpeaknow.host + '/interaction'
        });
    });

    return speaknow;
});