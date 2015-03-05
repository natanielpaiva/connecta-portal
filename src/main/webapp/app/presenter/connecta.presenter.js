define([
    'angular'
], function (angular) {
    var presenter = angular.module('connecta.presenter', ['angularFileUpload']);
    
    presenter.config(function($translatePartialLoaderProvider){
        $translatePartialLoaderProvider.addPart('presenter/datasource');
        $translatePartialLoaderProvider.addPart('presenter/singlesource');
        $translatePartialLoaderProvider.addPart('presenter/analysis');
    });
    
    presenter.run(function(applications){
        var appPresenter = applications.filter(function(app){
            return app.name === 'presenter';
        }).pop();
        
        presenter.lazy.value('presenterConfig', appPresenter);
        
        // Configurando os resources do backend
        presenter.lazy.value('presenterResources', {
            datasource: appPresenter.host+'/datasource',
            analysis: appPresenter.host+'/analysis',
            database: appPresenter.host+'/database',
            singlesource: appPresenter.host+'/media',
            attribute:appPresenter.host+'/attribute'
        });
    });

    presenter._routes = {
//        Utilizar esse formato        
       '/presenter/datasource': {
            controller: 'DatasourceListController',
            controllerUrl: 'presenter/datasource/controller/datasource-list',
            templateUrl: 'app/presenter/datasource/template/datasource-list.html'
        },
        '/presenter/datasource/new': {
            controller: 'DatasourceFormController',
            controllerUrl: 'presenter/datasource/controller/datasource-form',
            templateUrl: 'app/presenter/datasource/template/datasource-form.html'
        },
        '/presenter/datasource/view/:id': {
            controller: 'DatasourceViewController',
            controllerUrl: 'presenter/datasource/controller/datasource-view',
            templateUrl: 'app/presenter/datasource/template/datasource-view.html'
        },
        '/presenter/datasource/:id': {
            controller: 'DatasourceFormController',
            controllerUrl: 'presenter/datasource/controller/datasource-form',
            templateUrl: 'app/presenter/datasource/template/datasource-form.html'
        },
        '/presenter/singlesource/new': {
            controller: 'SingleSourceFormController',
            controllerUrl: 'presenter/singlesource/controller/single-source-form',
            templateUrl: 'app/presenter/singlesource/template/single-source-form.html'
        },
        '/presenter/singlesource/:id': {
            controller: 'SingleSourceFormController',
            controllerUrl: 'presenter/singlesource/controller/single-source-form',
            templateUrl: 'app/presenter/singlesource/template/single-source-form.html'
        },
        '/presenter/singlesource': {
            controller: 'SingleSourceListController',
            controllerUrl: 'presenter/singlesource/controller/single-source-list',
            templateUrl: 'app/presenter/singlesource/template/single-source-list.html'
        },
    };

    return presenter;
});