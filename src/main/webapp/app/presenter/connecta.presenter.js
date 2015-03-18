define([
    'angular'
], function (angular) {
    var presenter = angular.module('connecta.presenter', [
        'angularFileUpload',
        'ngAnimate'
    ]);

    presenter.config(function ($translatePartialLoaderProvider) {
        $translatePartialLoaderProvider.addPart('presenter/datasource');
        $translatePartialLoaderProvider.addPart('presenter/singlesource');
        $translatePartialLoaderProvider.addPart('presenter/analysis');
//        $translatePartialLoaderProvider.addPart('presenter/hierarchy');
    });

    presenter.value("fileExtensions", {
        JPG: {fileType: 'IMAGE', mimeTypes: ["image/jpeg", "image/pjpeg"]},
        PNG: {fileType: 'IMAGE', mimeTypes: ["image/png"]},
        MP4: {fileType: 'VIDEO', mimeTypes: ["video/mp4"]},
        XLS: {fileType: 'BINARY', mimeTypes: ["application/excel"]},
        DOC: {fileType: 'BINARY', mimeTypes: ["application/msword"]},
        DOCX: {fileType: 'BINARY', mimeTypes: ["application/vnd.openxmlformats-officedocument.wordprocessingml.document"]},
        PPT: {fileType: 'BINARY'},
        PPTX: {fileType: 'BINARY'}
    });

    presenter.run(function (applications) {
        var appPresenter = applications.filter(function (app) {
            return app.name === 'presenter';
        }).pop();

        presenter.lazy.value('presenterConfig', appPresenter);

        // Configurando os resources do backend
        presenter.lazy.value('presenterResources', {
            datasource: appPresenter.host + '/datasource',
            analysis: appPresenter.host + '/analysis',
            singlesource: appPresenter.host + '/media',
            attribute: appPresenter.host + '/attribute',
            hierarchy: appPresenter.host + '/hierarchy'
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
        '/presenter/singlesource/view/:id': {
            controller: 'SingleSourceViewController',
            controllerUrl: 'presenter/singlesource/controller/single-source-view',
            templateUrl: 'app/presenter/singlesource/template/single-source-view.html'
        },
        '/presenter/analysis/new': {
            controller: 'AnalysisFormController',
            controllerUrl: 'presenter/analysis/controller/analysis-form',
            templateUrl: 'app/presenter/analysis/template/analysis-form.html'
        },
        '/presenter/hierarchy/new': {
            controller: 'HierarchyFormController',
            controllerUrl: 'presenter/hierarchy/controller/hierarchy-form',
            templateUrl: 'app/presenter/hierarchy/template/hierarchy-form.html'
        }
    };

    return presenter;
});