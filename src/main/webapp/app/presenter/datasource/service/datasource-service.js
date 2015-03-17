define([
    'angular',
    'connecta.presenter'
], function (angular, presenter) {
    
    return presenter.lazy.service('DatasourceService', function(presenterResources, $http){
        var types = [
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
                id: 'bi',
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
        
        this.getTypes = function() {
            return types;
        };
        
        var getTypeUrl = function(datasource) {
            return presenterResources.datasource+'/'+datasource.type.id;
        };
        
        this.save = function(datasource){
            var url = getTypeUrl(datasource);
            
            var datasourceCopy = angular.copy(datasource);
            datasourceCopy.type = datasourceCopy.type.id.toUpperCase();
            
            return $http.post(url, datasourceCopy);
        };
        
        this.list = function(params){
            var url = presenterResources.datasource;
            return $http.get(url, {
                params: params
            });
        };
        
        this.excluir = function(id){
            var url = presenterResources.datasource +'/'+id;
            return $http.delete(url);
        };
        
        this.getById = function(id){
            var url = presenterResources.datasource +'/'+id;
            return $http.get(url);
        };
        
    });
    
});