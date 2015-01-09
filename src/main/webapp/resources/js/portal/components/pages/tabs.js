/**
 * Permite organizar as páginas abertas por aplicação
 *
 * @param {type} connecta
 * @param {String} template
 * @returns {undefined}
 */
define([
    'connecta',
    'text!./tpl/tabs.html',
    './tabs-app',
    './tabs-history'
], function(connecta, template) {

    connecta.directive('appPagesTabs', function() {
        return {
            restrict: 'A',
            replace: true,
            template: template,
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