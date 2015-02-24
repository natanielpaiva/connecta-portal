define([
    'connecta.presenter',
    'presenter/datasource/service/datasource-service'
], function (presenter) {
    return presenter.lazy.controller('DatasourceFormController', function ($scope, DatasourceService, $location, $routeParams) {

        $scope.types = DatasourceService.getTypes();

        $scope.typedDatasource = {};

        if ($routeParams.id) {
            DatasourceService.getById($routeParams.id).then(function (response) {
                var type = $scope.types.filter(function (value) {
                    return value.id.toUpperCase() === response.data.type;
                }).pop();

                $scope.typedDatasource = response.data;
                $scope.typedDatasource.type = type;
            });
        } else {
            $scope.typedDatasource.type = $scope.types[0];
            /**
             * Parâmetros do WebService
             */
            $scope.typedDatasource.parameters = [
                {
                    params: '',
                    value: ''
                }
            ];
        }

        $scope.submit = function () {
            DatasourceService.save($scope.typedDatasource).then(function () {
                $location.path('presenter/datasource');
                //console.log(argument);
            }, function (response) {
                //console.log(response);
            });
        };

        $scope.addMethodParam = function () {
            $scope.typedDatasource.parameters.push({});
        };
        $scope.removeMethodParam = function (param) {
            /**
             *sempre deve manter um campo
             */
            if ($scope.typedDatasource.parameters.length !== 1) {
                $scope.typedDatasource.parameters.splice(
                        $scope.typedDatasource.parameters.indexOf(param), 1);
            }
        };
    });
});