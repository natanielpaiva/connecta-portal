define([
    'angular'
], function (angular) {
    var presenter = angular.module('connecta.presenter', ['angularFileUpload']);
    
    presenter.config(function($translatePartialLoaderProvider){
        $translatePartialLoaderProvider.addPart('presenter/datasource');
    });
    
    presenter.run(function(applications){
        var appPresenter = applications.filter(function(app){
            return app.name === 'presenter';
        }).pop();
        
        presenter.lazy.value('presenterConfig', appPresenter);
        
        // Configurando os resources do backend
        presenter.lazy.value('presenterResources', {
            datasource: appPresenter.host+'/datasource',
            singlesource: appPresenter.host+'/media'
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
        '/presenter/single-source/new': {
            controller: 'SingleSourceFormController',
            controllerUrl: 'presenter/single-source/controller/single-source-form',
            templateUrl: 'app/presenter/single-source/template/single-source-form.html'
        }
    };

    return presenter;
});