define([
    'connecta.presenter'
], function (presenter) {
    
    return presenter.lazy.service('DatasourceService', function(presenterResources, $http){
        this.save = function(datasource){
            return $http.post(presenterResources.datasource, datasource);
        };
    });
    
});