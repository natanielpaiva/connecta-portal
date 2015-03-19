define([
    'connecta.speaknow',
    'speaknow/company/service/company-service'
], function (speaknow) {
    return speaknow.lazy.controller('CompanyFormController', function ($scope, CompanyService, $location, $routeParams) {

        $scope.company = {};

        $scope.submit = function () {
            CompanyService.save($scope.fileQuad, $scope.fileRect, $scope.company).then(function () {
                $location.path('speaknow/company');
            }, function (response) {});
        };

        $scope.fileQuadDropped = function (files) {
            if (files && files.length) {
                $scope.fileQuad = files[0];
                $("#file-quad").html($scope.fileQuad.name);
            }
        };
        
        $scope.fileRectDropped = function (files) {
            if (files && files.length) {
                $scope.fileRect = files[0];
                $("#file-rect").html($scope.fileRect.name);
            }
        };
    });
});