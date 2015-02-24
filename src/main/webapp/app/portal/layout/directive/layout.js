define([
    'connecta.portal',
    'portal/layout/directive/header',
    'portal/layout/directive/sidebar',
    'portal/layout/directive/pages',
    'portal/layout/directive/search-embedded'
], function (portal) {
    /**
     * Componente usado para renderizar e manter o header do portal
     *
     * @returns {undefined}
     */
    return portal.directive('appLayout', function () {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: 'app/portal/layout/directive/template/layout.html',
            link: function (scope, element) {

                /**
                 * Evento para exibir a barra lateral
                 */
                scope.$on('sidebar.show', function () {
                    element.removeClass('hide-sidebar');
                });

                /**
                 * Evento para ocultar a barra lateral
                 */
                scope.$on('sidebar.hide', function () {
                    element.addClass('hide-sidebar');
                });

                /**
                 * Evento para ocultar a lista de tabs
                 */
                scope.$on('page.hideTabs', function () {
                    element.removeClass('show-tabs');
                });

                /**
                 * Evento para exibir a lista de tabs
                 */
                scope.$on('page.showTabs', function () {
                    element.addClass('show-tabs');
                });
            }
        };
    });
});

