define([
    'connecta.presenter',
    'presenter/datasource/service/datasource-service'
], function (presenter) {
    return presenter.lazy.controller('DatasourceListController', function ($scope, DatasourceService) {

        $scope.datasource = null;
        DatasourceService.list().then(function (response) {
            $scope.datasource = response.data;
            console.log(response.data);
        }, function (response) {
            console.log(response);
        });
        
        $scope.excluir =  function(id){
            //alert(id);
            DatasourceService.excluir(id).then(function (response) {
            //$scope.datasource = response.data;
            console.log(response.data);
        }, function (response) {
            console.log(response);
        });
        };
    });
});