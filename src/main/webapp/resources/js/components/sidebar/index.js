/**
 * Componente usado para renderizar e manter a barra lateral
 */
define([
    'connecta',
    'text!./tpl/index.html'
], function(connecta, template) {

    connecta.directive('appSidebar', [
        'applicationsService',
        function(applicationsService) {
            return {
                restrict: 'A',
                replace: true,
                template: template,
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
        }
    ]);
});

