define([
    'connecta.presenter',
    'portal/layout/service/autocomplete'
], function (presenter) {

    return presenter.lazy.service('GroupService', function (presenterResources, $http, $autocomplete) {
        var types = [
            {
                id: 'FOLDER',
                name: 'FOLDER',
            },
            {
                id: 'GALLERY',
                name: 'GALLERY',
            }
        ];
        
        var typeFilter = [
            {
                id: 'GROUP.SELECT_FILE',
                name: 'GROUP.SELECT_FILE',
                template: '_group-select-file.html'
            },
            {
                id: 'GROUP.FILTER',
                name: 'GROUP.FILTER',
                template: '_group-filter.html'
            }
        ];
        
        this.getAttribute = function (value) {
            return $autocomplete(presenterResources.singlesource + "/auto-complete", {
                name: value
            }).then(function (response) {
                return response.data.map(function (item) {
                    console.log(item);
                    return item;
                });
            });
        };
        

        this.getTypes = function () {
            return types;
        };
        
        this.getTypeFilter = function () {
            return typeFilter;
        };
        

        this.list = function () {
            var url = presenterResources.group;
            return $http.get(url);
        };

        this.delete = function (id) {
            var url = presenterResources.group + '/' + id;
            return $http.delete(url);
        };

        this.getById = function (id) {
            var url = presenterResources.group + '/' + id;
            return $http.get(url);
        };
        
        this.getFileById = function (id) {
            return presenterResources.singlesource + '/' + id + '/binary';
        };
        
        this.getSingleSourceAttributeById = function (id) {
            var url = presenterResources.singlesource + "/" + id;
            return $http.get(url);
        };
        
    });



});