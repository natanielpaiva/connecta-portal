define([
    'connecta.presenter',
    'presenter/datasource/service/datasource-service'
], function (presenter) {
    return presenter.lazy.controller('DatasourceFormController', function ($scope, DatasourceService) {

        $scope.types = [
            {
                id: 'database',
                name: 'Database',
                template: '_datasource-database.html'
            },
            {
                id: 'endeca',
                name: 'Endeca',
                template: '_datasource-endeca.html'
            },
            {
                id: 'hdfs',
                name: 'HDFS',
                template: '_datasource-hdfs.html'
            },
            {
                id: 'obiee',
                name: 'OBIEE',
                template: '_datasource-obiee.html'
            },
            {
                id: 'solr',
                name: 'SOLR',
                template: '_datasource-solr.html'
            },
            {
                id: 'webservice',
                name: 'WebService',
                template: '_datasource-webservice.html'
            }
        ];

        $scope.typedDatasource = {
            datasource: {
                name: '',
                type: $scope.types[0]
            }
        };

        $scope.submit = function () {
            DatasourceService.save($scope.typedDatasource).then(function(){
                console.log(arguments);
            }, function(response){
                console.log(response);
            });
        };


    });


});