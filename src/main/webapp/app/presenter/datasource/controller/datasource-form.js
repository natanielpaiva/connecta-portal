define([
    'connecta.presenter',
    'presenter/datasource/service/datasource-service'
], function (presenter) {
    return presenter.lazy.controller('DatasourceFormController', function ($scope, DatasourceService, $location) {

        $scope.types = DatasourceService.getTypes();

        $scope.typedDatasource = {
            datasource: {
                name: '',
                type: $scope.types[0]
            }
        };

        $scope.submit = function () {
            DatasourceService.save($scope.typedDatasource).then(function(){
                $location.path('presenter/datasource');
                console.log(argument);
            }, function(response){
                console.log(response);
            });
        };
        
        /**
         * Parâmetros do WebService
         */
        $scope.typedDatasource.parameters = [
            { 
                key: '',
                value: ''
            }
        ];
        
        $scope.addMethodParam = function(){
            $scope.typedDatasource.parameters.push({});
        };
        $scope.removeMethodParam = function(index){
            $scope.typedDatasource.parameters.splice(index, 1);
        };
        


    });


});