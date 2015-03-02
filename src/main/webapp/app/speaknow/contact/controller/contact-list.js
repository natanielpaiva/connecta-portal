define([
    'connecta.speaknow',
    'speaknow/contact/service/contact-service'
], function (speaknow) {
    return speaknow.lazy.controller('ContactListController', function ($scope, ContactService) {

        $scope.contacts = null;

        ContactService.list().then(function (response) {
            $scope.contacts = response.data;
        }, function (response) {});
        
    });
});
