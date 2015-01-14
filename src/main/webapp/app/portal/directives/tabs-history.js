/**
 * Permite organizar as páginas abertas por aplicação
 */
define([
    'connecta.portal'
], function(portal) {
    return portal.directive('appPagesTabsHistory', function(pageService, portalDirectiveTemplate) {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: portalDirectiveTemplate('tabs-history'),
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
    });
});