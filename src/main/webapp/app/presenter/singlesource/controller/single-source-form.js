define([
    'connecta.presenter',
    'presenter/singlesource/service/singlesource-service'
], function (presenter) {
    return presenter.lazy.controller('SingleSourceFormController', function ($scope, SingleSourceService, $routeParams, $location, $http) {

        $scope.myFiles = [];
        $scope.types = SingleSourceService.getTypes();
        $scope.typeSingleSource = {};
        $scope.file = "";

        $scope.getAttributes = function (val) {
            return SingleSourceService.getAttribute(val);
        };


        if ($routeParams.id) {
            $scope.typeSingleSource.type = $scope.types[0];
            SingleSourceService.getById($routeParams.id).then(function (response) {
                var type = $scope.types.filter(function (value) {
                    return value.id.toUpperCase() === response.data.type;
                }).pop();

                $scope.typeSingleSource = response.data;
                $scope.typeSingleSource.type = type;


                if ($scope.typeSingleSource.singleSourceAttributes.length === 0) {
                    $scope.typeSingleSource.singleSourceAttributes = [
                        {
                            attribute: "",
                            value: ''
                        }
                    ];
                }
            });
        } else {
            $scope.typeSingleSource.type = $scope.types[0];

            /**
             * Parâmetros do singleSourceAttributes
             */
            $scope.typeSingleSource.singleSourceAttributes = [
                {
                    attribute: "",
                    value: ''
                }
            ];
        }

        $scope.fileDropped = function (files) {
            if (files && files.length) {
                $scope.file = files[0];
                $(".set-file").html($scope.file.name);
            }
        };

        $scope.submit = function () {

            if ($scope.typeSingleSource.type.id === "FILE") {
                if ($scope.file !== "") {
                    $scope.typeSingleSource.fileType = $scope.file.type;
                    $scope.typeSingleSource.type = "FILE";
                    $scope.typeSingleSource.filename = $scope.file.name;

                    SingleSourceService.save($scope.file, $scope.typeSingleSource).then(function () {
                        $location.path('presenter/singlesource');
                    }, function (response) {
                    });
                } else {
                    $scope.typeSingleSource.type = "FILE";
                    SingleSourceService.save($scope.file, $scope.typeSingleSource).then(function () {
                        $location.path('presenter/singlesource');
                    }, function (response) {
                    });
                }
            } else {
                if ($scope.typeSingleSource.id) {
                    SingleSourceService.updateUrl($scope.typeSingleSource).then(function () {
                        $location.path('presenter/singlesource');
                    }, function (response) {
                    });
                } else {
                    $scope.typeSingleSource.urlType = "url";
                    SingleSourceService.saveUrl($scope.typeSingleSource).then(function () {
                        $location.path('presenter/singlesource');
                    }, function (response) {
                    });
                }
            }

            console.log($scope.typeSingleSource);

            //SingleSourceService.save($scope.file, $scope.singlesource);
            //console.log($scope.typeSingleSource);
//            DatasourceService.save($scope.typedDatasource).then(function () {
//                $location.path('presenter/datasource');
//                //console.log(argument);
//            }, function (response) {
//                //console.log(response);
//            });
        };

        $scope.addSingleSourceAttribute = function () {
            $scope.typeSingleSource.singleSourceAttributes.push({});
        };
        $scope.removeSingleSourceAttribute = function (param) {
            /**
             *sempre deve manter um campo
             */
            if ($scope.typeSingleSource.singleSourceAttributes.length !== 1) {
                $scope.typeSingleSource.singleSourceAttributes.splice(
                        $scope.typeSingleSource.singleSourceAttributes.indexOf(param), 1);
            }
        };

    });


});