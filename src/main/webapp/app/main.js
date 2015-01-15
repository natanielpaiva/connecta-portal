require.config({
    paths: {
        'bower_components':'../bower_components',
        'text': '../bower_components/requirejs-text/text',
        'json': '../bower_components/requirejs-json/json',
        'domReady': '../bower_components/requirejs-domready/domReady',
        'jquery' : '../bower_components/jquery/dist/jquery.min',
        'angular': '../bower_components/angular/angular.min',
        'angular-route': '../bower_components/angular-route/angular-route.min',
        'angular-resource': '../bower_components/angular-resource/angular-resource.min',
        'angular-ui-bootstrap': '../bower_components/angular-bootstrap/ui-bootstrap-tpls.min',
        'angular-ng-table':'../bower_components/ng-table/ng-table.min',
        'portal' : 'portal',
        'collector' : 'collector',
        'presenter' : 'presenter',
        'maps' : 'maps',
        'connecta.portal': 'portal/connecta.portal',
        'connecta.collector': 'collector/connecta.collector',
        'connecta.presenter': 'presenter/connecta.presenter',
        'connecta.maps': 'maps/connecta.maps'
    },
    shim: {
        'angular': {
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular']
        },
        'angular-resource': {
            deps: ['angular']
        },
        'angular-ui-bootstrap': {
            deps: ['angular']
        },
        'bower_components/angular-animate/angular-animate.min':{
            deps: ['angular']
        },
        'bower_components/angular-cookies/angular-cookies.min':{
            deps: ['angular']
        },
        'bower_components/angular-touch/angular-touch.min':{
            deps: ['angular']
        },
        'bower_components/angular-i18n/angular-locale_pt-br': {
            deps: ['angular']
        },
        'angular-ng-table':{
            deps: ['angular']
        },
        'bower_components/jquery-ui-custom/index': {
            deps: ['jquery']
        },
        'bower_components/bootstrap/dist/js/bootstrap.min': {
            deps: ['jquery']
        },
        'bower_components/jquery-slimscroll/jquery.slimscroll.min':{
            deps:['jquery']
        },
        'connecta': {
            deps: [
                'jquery',
                'bower_components/jquery-ui-custom/index',
                'bower_components/jquery-slimscroll/jquery.slimscroll.min',
                'bower_components/bootstrap/dist/js/bootstrap.min',
                'search-expression'
            ]
        }
    }
});

define([
    'connecta'
], function(connecta) {
    connecta.appBootstrap.init();
});