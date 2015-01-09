/**
 * Controller principal da aplicação
 */
define([
    'angular',
    'components/layout/index'
], function() {

    var app = angular.module('connecta');
    return app.controller('appMain', [
        '$scope',
        'applicationsService',
        function($scope, applicationsService) {
            // Atualiza a lista de aplicações do portal
            applicationsService.setApplications(app.conf.applications);

            resetStyles = function() {
                //Carrega os estilos personalizados da aplicação
                $scope.stylesheets = [];

                // adiciona o estilo base de todas as aplicações registrada
                for (var a in app.conf.applications) {
                    if (!app.conf.applications.hasOwnProperty(a)) {
                        continue;
                    }
                    var application = app.conf.applications[a];
                    $scope.stylesheets.push(application.host + '/resources/css/base.css');
                }
            };

            resetStyles();

            // Evento ao atualizar a aplicação em execução
            $scope.$on('applications.change', function(event) {
                resetStyles();

                // Adiciona os estilos personalizados da aplicação atual
                var appInstance = applicationsService.getInstance();
                if (appInstance && appInstance.styles) {
                    $.each(appInstance.styles, function(i, item) {
                        $scope.stylesheets.push(appInstance.host + '/resources/css/' + item);
                    });
                }

                $scope.actualAppName = appInstance.name;
            });
        }
    ]);
});