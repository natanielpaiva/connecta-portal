define([
    'connecta.speaknow'
], function (presenter) {
    return presenter.lazy.service('ContactService', function (speaknowResources, $http) {

        this.list = function () {
            var url = speaknowResources.contact;
            return $http.get(url);
        };
        
        this.save = function(contact){
            var url = speaknowResources.contact;
            return $http.post(url, contact);
        };
        
    });
});