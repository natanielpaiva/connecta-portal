define([
    'connecta.presenter',
    'presenter/datasource/service/datasource-service'
], function (presenter) {
    return presenter.lazy.controller('DatasourceViewController', function ($scope, DatasourceService, $routeParams, $location) {

        DatasourceService.getById($routeParams.id).then(function (response) {

            $scope.datasource = response.data;
            
            $scope.excluir = function (id) {
            DatasourceService.excluir(id).then(function (response) {
                     $location.path('presenter/datasource');
            }, function (response) {
                
            });
        };
        });

    });
});