define([
    'connecta.speaknow',
    'speaknow/company/service/company-service'
], function (speaknow) {
    return speaknow.lazy.controller('CompanyListController', function ($scope, CompanyService) {
        $scope.companies = null;
        
        CompanyService.list(1, 10).then(function(response){
            $scope.companies = response.data;
        });
        
    });
});