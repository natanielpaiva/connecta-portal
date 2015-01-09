/**
 * Permite organizar as páginas abertas por aplicação
 *
 * @param {type} angular
 * @param {String} template
 * @returns {undefined}
 */
define([
    'connecta',
    'text!./tpl/tabs-history.html'
], function(connecta, template) {

    connecta.directive('appPagesTabsHistory', ['pageService', function(pageService) {
        return {
            restrict: 'A',
            replace: true,
            template: template,
            scope: {
                page: '='
            },
            link: function($scope, $element, attrs) {

                $scope.pageHistory = pageService.getPageHistory();

                $scope.$on('page.change', function() {
                    $scope.pageHistory = pageService.getPageHistory();
                });

                $scope.$on('page.remove', function() {
                    $scope.pageHistory = pageService.getPageHistory();
                });

                // barra de rolagem da lista de páginas
                var eleHeight = window.screen.height;
                eleHeight = eleHeight - (eleHeight * 22.5 / 100);
                $('> ul', $element).slimScroll({
                    color: '#a1b2bd',
                    size: '4px',
                    height: eleHeight,
                    alwaysVisible: false
                });
            }
        };
    }]);
});