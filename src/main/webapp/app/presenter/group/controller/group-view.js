define([
    'connecta.presenter',
    'presenter/group/service/group-service'
], function (presenter) {
    return presenter.lazy.controller('GroupViewController', function ($scope, GroupService, $routeParams, $location, fileExtensions) {

        GroupService.getById($routeParams.id).then(function (response) {

            $scope.group = response.data;
            
            $scope.excluir = function (id) {
            GroupService.excluir(id).then(function (response) {
                     $location.path('presenter/group');
            }, function (response) {
                
            });
        };
        });

    });
});