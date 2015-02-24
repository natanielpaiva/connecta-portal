define([
    'connecta.presenter',
    'presenter/datasource/service/datasource-service'
], function (presenter) {
    return presenter.lazy.controller('DatasourceViewController', function ($scope, DatasourceService, $routeParams) {

        DatasourceService.getById($routeParams.id).then(function (response) {

            $scope.datasource = response.data;

        });

    });
});