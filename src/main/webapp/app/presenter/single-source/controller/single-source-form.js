define([
    'connecta.presenter',
    'presenter/single-source/service/singlesource-service'
], function (presenter) {
    return presenter.lazy.controller('SingleSourceFormController', function ($scope, SingleSourceService) {
        $scope.singlesource = {name: "batata", description: "asdfad", type: "FILE", filename: "Batata"};
        $scope.myFiles = [];

        $scope.fileDropped = function (files) {
            if (files && files.length) {
                var file = files[0];
                SingleSourceService.save(file, $scope.singlesource);
            }
            console.log(arguments);
        };

    });


});