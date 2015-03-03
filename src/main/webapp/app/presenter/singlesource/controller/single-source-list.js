define([
    'connecta.presenter',
    'presenter/singlesource/service/singlesource-service'
], function (presenter) {
    return presenter.lazy.controller('SingleSourceListController', function ($scope, SingleSourceService) {
        $scope.types = SingleSourceService.getTypes();

        $scope.filter = false;

        $scope.singlesource = null;
        SingleSourceService.list().then(function (response) {
            $scope.singlesource = response.data;
            //console.log(response.data);
        }, function (response) {
            //console.log(response);
        });

        $scope.excluir = function (id) {
            SingleSource.delete(id).then(function (response) {
                //Retira um item da lista de datasource
                $scope.singlesource.splice(
                        $scope.singlesource.indexOf(id), 1);

            }, function (response) {
                //console.log(response);
            });
        };
    });
});