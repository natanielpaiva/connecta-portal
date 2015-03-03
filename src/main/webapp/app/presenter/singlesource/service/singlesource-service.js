define([
    'connecta.presenter',
    'portal/layout/service/autocomplete'
], function (presenter) {

    return presenter.lazy.service('SingleSourceService', function ($autocomplete, presenterResources, $http, $upload) {
        var types = [
            {
                id: 'URL',
                name: 'URL',
                template: '_single-source-url.html'
            },
            {
                id: 'FILE',
                name: 'FILE',
                template: '_single-source-file.html'
            }
        ];

        var _fixAttributes = function (singlesource) {
            angular.forEach(singlesource.singleSourceAttributes, function (attribute) {
                if (angular.isString(attribute.attribute)) {
                    attribute.attribute = {name: attribute.attribute};
                }
            });
        };

        this.getAttribute = function (value) {
            return $autocomplete(presenterResources.attribute, {
                name: value
            }).then(function (response) {
                return response.data.map(function (item) {
                    return item;
                });
            });
        };

        this.save = function (file, singlesource) {
            _fixAttributes(singlesource);
            return $upload.upload({
                url: presenterResources.singlesource + "/file",
                method: 'POST',
                fields: {
                    singlesource: singlesource
                },
                file: file
            }).progress(function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
            }).success(function (data, status, headers, config) {
                console.log(data);
            });
        };

        this.updateUrl = function (singlesource) {
            _fixAttributes(singlesource);
            var url = getTypeUrl(singlesource);

            var singlesourceCopy = angular.copy(singlesource);
            singlesourceCopy.type = singlesourceCopy.type.id;

            return $http.put(url, singlesourceCopy);
        };

        this.saveUrl = function (singlesource) {
            _fixAttributes(singlesource);
            var url = getTypeUrl(singlesource);

            var singlesourceCopy = angular.copy(singlesource);
            singlesourceCopy.type = singlesourceCopy.type.id;

            return $http.post(url, singlesourceCopy);
        };

        var getTypeUrl = function (singlesource) {
            return presenterResources.singlesource + '/' + singlesource.type.id.toLowerCase();
        };

        this.getTypes = function () {
            return types;
        };

        this.list = function () {
            var url = presenterResources.singlesource;
            return $http.get(url);
        };

        this.delete = function (id) {
            var url = presenterResources.singlesource + '/' + id;
            return $http.delete(url);
        };

        this.getById = function (id) {
            var url = presenterResources.singlesource + '/' + id;
            return $http.get(url);
        };

    });



});