define([
    'connecta.presenter',
    'presenter/singlesource/service/singlesource-service'
], function (presenter) {
    return presenter.lazy.controller('SingleSourceViewController', function ($scope, SingleSourceService, $routeParams, $location) {

        SingleSourceService.getById($routeParams.id).then(function (response) {

            $scope.singlesource = response.data;
            
            $scope.singlesource.binaryFile = SingleSourceService.getFileById($routeParams.id);
            
            $scope.excluir = function (id) {
            SingleSourceService.excluir(id).then(function (response) {
                     $location.path('presenter/singlesource');
            }, function (response) {
                
            });
            
            
            
        };
        });

    });
});