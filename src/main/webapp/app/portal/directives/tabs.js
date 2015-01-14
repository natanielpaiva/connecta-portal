/**
 * Permite organizar as páginas abertas por aplicação
 */
define([
    'connecta.portal',
    'portal/directives/tabs-app',
    'portal/directives/tabs-history'
], function(portal) {
    return portal.directive('appPagesTabs', function(portalDirectiveTemplate) {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: portalDirectiveTemplate('tabs'),
            scope: {
                page: '='
            },
            link: function($scope, $element, attrs) {

                $scope.listTabsAsHistory = false;
                $scope.showTabs = false;


                /**
                 * Evento para exibir a listagem de páginas abertas
                 *
                 * Computa as páginas abertas e exibe-as
                 */
                $scope.$on('page.showTabs', function() {
                    $scope.showTabs = true;
                });

                /**
                 * Evento para ocultar a listagem de páginas abertas
                 */
                $scope.$on('page.hideTabs', function() {
                    $scope.showTabs = false;
                });
            }
        };
    });
});