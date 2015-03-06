define([
    'connecta.presenter',
    'presenter/singlesource/service/singlesource-service'
], function (presenter) {
    return presenter.lazy.controller('SingleSourceListController', function ($scope, SingleSourceService, $location) {
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
            SingleSourceService.delete(id).then(function () {
                $location.path('presenter/singlesource');
            });
        };
    });
});