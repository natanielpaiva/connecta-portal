require([
    'connecta.portal'
], function(portal){
    return portal.controller('HomeController', function($scope, $translate){
        $scope.setLang = function(lang) {
            $translate.use(lang);
        };
    });
});