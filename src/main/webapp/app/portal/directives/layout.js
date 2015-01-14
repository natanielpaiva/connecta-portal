/**
 * Componente usado para renderizar e manter o header do portal
 *
 * @param {type} angular
 * @returns {undefined}
 */
define([
    'connecta.portal',
    'portal/directives/header',
    'portal/directives/sidebar',
    'portal/directives/pages',
    'portal/directives/search-embedded'
], function (portal) {
    return portal.directive('appLayout', function (portalDirectiveTemplate) {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: portalDirectiveTemplate('layout'),
            link: function ($scope, $element, attrs) {

                /**
                 * Evento para exibir a barra lateral
                 */
                $scope.$on('sidebar.show', function () {
                    $element.removeClass('hide-sidebar');
                });

                /**
                 * Evento para ocultar a barra lateral
                 */
                $scope.$on('sidebar.hide', function () {
                    $element.addClass('hide-sidebar');
                });

                /**
                 * Evento para ocultar a lista de tabs
                 */
                $scope.$on('page.hideTabs', function () {
                    $element.removeClass('show-tabs');
                });

                /**
                 * Evento para exibir a lista de tabs
                 */
                $scope.$on('page.showTabs', function () {
                    $element.addClass('show-tabs');
                });
            }
        };
    });
});

