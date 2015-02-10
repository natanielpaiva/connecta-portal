define([
    'connecta.presenter'
], function (presenter) {
    
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
        
        this.getTypes = function() {
            return types;
        };
        
        var getTypeUrl = function(datasource) {
            return presenterResources.datasource+'/'+datasource.datasource.type.id;
        };
        
        this.save = function(datasource){
            var url = getTypeUrl(datasource);
            
            var datasourceCopy = angular.copy(datasource);
            datasourceCopy.datasource.type = datasourceCopy.datasource.type.id.toUpperCase();
            
            return $http.post(url, datasourceCopy);
        };
    });
    
});