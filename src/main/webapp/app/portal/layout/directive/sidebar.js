define([
    'connecta.portal'
], function(portal) {
    /**
     * Componente usado para renderizar e manter a barra lateral
     */
    return portal.directive('appSidebar', function(applicationsService) {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: 'app/portal/layout/directive/template/sidebar.html',
            link: function(scope) {
                scope.menu = [];

                scope.$on('applications.change', function() {
                    var appInstance = applicationsService.getInstance();
                    if (appInstance) {
                        scope.menu = appInstance.navigation;
                    }
                });
            }
        };
    });
});

