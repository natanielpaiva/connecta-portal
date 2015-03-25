
define([
    'connecta.presenter',
    'presenter/group/service/group-service'
], function (presenter) {
    return presenter.lazy.controller('GroupFormController', function ($scope, GroupService, $routeParams, $location, fileExtensions, $autocomplete, presenterResources) {

        $scope.types = GroupService.getTypes();
        $scope.typeFilter = GroupService.getTypeFilter();
        $scope.group = {};
        $scope.attribute = {name: ""};
        $scope.paths = [];
        $scope.singleSources = {};

        $scope.onDragComplete = function (data, evt) {
            console.log("drag success, data:", data);
        };
        $scope.onDropComplete = function (data, evt) {
            console.log("drop success, data:", data);
        };

        $scope.getAttributes = function (value) {
            return $autocomplete(presenterResources.singlesource + "/auto-complete", {
                name: value
            }).then(function (response) {
                return response.data.map(function (item) {
                    $scope.setGallery(item);
                    return item;
                });
            });
        };

        if ($routeParams.id) {

            $scope.group.type = $scope.types[0];
            GroupService.getById($routeParams.id).then(function (response) {
                var type = $scope.types.filter(function (value) {
                    return value.id.toUpperCase() === response.data.type;
                }).pop();

                $scope.group = response.data;
                $scope.group.type = type;
                $scope.group.path = GroupService.getFileById($routeParams.id);

                if ($scope.group.singleSourceAttributes.length === 0) {
                    $scope.group.singleSourceAttributes = [
                        {
                            attribute: "",
                            value: ''
                        }
                    ];
                } else {
                    for (var key in $scope.group.singleSourceAttributes) {

                        var value = 0;
                        for (var v in $scope.optionsAttributeTypes) {
                            if ($scope.optionsAttributeTypes[v].label === $scope.group.singleSourceAttributes[key].attribute.type)
                                value = v;
                        }

                        $scope.group.singleSourceAttributes[key].attributeType = $scope.optionsAttributeTypes[value];
                    }
                }

                if ($scope.group.fileType !== undefined) {
                    $scope.fileExtensions = fileExtensions[$scope.group.fileType];
                }

            });
        } else {
            $scope.group.typeFilter = $scope.typeFilter[0];
            $scope.group.path = "";
            $scope.group.type = $scope.types[0];

        }

        $scope.change = function () {
            if ($scope.attribute.name.id !== undefined) {

                $scope.paths = [];
                GroupService.getSingleSourceAttributeById($scope.attribute.name.id)
                        .then(function (response) {
                            if (response.data.type === 'FILE') {
                                $scope.paths[0] = {};
                                $scope.paths[0].path = GroupService
                                        .getFileById(response.data.id);
                                $scope.paths[0].fileType = fileExtensions[response.data.fileType].fileType;
                                $scope.paths[0].id = response.data.id;
                                $scope.paths[0].tamanho = 0;
                            }

                        }, function (response) {
                            console.log(response);
                        });
            }
        };

        $scope.setGallery = function (data) {
            var indice = $scope.paths.length;

            if (data.id !== undefined) {
                GroupService.getSingleSourceAttributeById(data.id)
                        .then(function (response) {
                            if (response.data.type === 'FILE') {
                                $scope.paths[indice] = {};
                                $scope.paths[indice].path = GroupService
                                        .getFileById(response.data.id);
                                $scope.paths[indice].fileType = fileExtensions[response.data.fileType].fileType;
                                $scope.paths[indice].id = data.id;
                                $scope.paths[indice].tamanho = 0;
                                for (var path in $scope.paths) {
                                    if ($scope.paths[path].id === $scope.paths[indice].id) {
                                        $scope.paths[indice].tamanho++;
                                        if ($scope.paths[indice].tamanho > 1) {
                                            $scope.paths.splice(indice, 1);
                                        }
                                    }

                                }

                            }
                        }, function (response) {
                            console.log(response);
                        });
            }
        };

    });
});