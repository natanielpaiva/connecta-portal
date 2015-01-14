/**
 * Componente usado para renderizar e manter a barra lateral
 */
define([
    'connecta.portal'
], function(portal) {
    return portal.directive('appSidebar', function(applicationsService, portalDirectiveTemplate) {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: portalDirectiveTemplate('sidebar'),
            link: function($scope, element, attrs) {
                $scope.menu = [];

                $scope.$on('applications.change', function() {

                    var appInstance = applicationsService.getInstance();
                    if (appInstance) {
                        $scope.menu = appInstance.navigation;
                    }


                });
            }
        };
    });
});

