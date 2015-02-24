define([
    'connecta.presenter',
    'presenter/datasource/service/datasource-service'
], function (presenter) {
    return presenter.lazy.controller('DatasourceListController', function ($scope, DatasourceService) {

        $scope.types = DatasourceService.getTypes();

        $scope.filter = false;

        $scope.datasource = null;
        DatasourceService.list().then(function (response) {
            $scope.datasource = response.data;
            //console.log(response.data);
        }, function (response) {
            //console.log(response);
        });

        $scope.excluir = function (id) {
            DatasourceService.excluir(id).then(function (response) {
                //Retira um item da lista de datasource
                $scope.datasource.splice(
                        $scope.datasource.indexOf(id), 1);

            }, function (response) {
                //console.log(response);
            });
        };
    });
});