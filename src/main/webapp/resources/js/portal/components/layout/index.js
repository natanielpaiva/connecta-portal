/**
 * Componente usado para renderizar e manter o header do portal
 *
 * @param {type} angular
 * @returns {undefined}
 */
define([
    'connecta',
    'text!./tpl/index.html',
    'portal/components/header/index',
    'portal/components/sidebar/index',
    'portal/components/pages/index',
    'portal/components/search/embedded'
], function(connecta, template) {

    connecta.directive('appLayout', [
        function() {
            return {
                restrict: 'A',
                replace: true,
                template: template,
                link: function($scope, $element, attrs) {

                    /**
                     * Evento para exibir a barra lateral
                     */
                    $scope.$on('sidebar.show', function() {
                        $element.removeClass('hide-sidebar');
                    });

                    /**
                     * Evento para ocultar a barra lateral
                     */
                    $scope.$on('sidebar.hide', function() {
                        $element.addClass('hide-sidebar');
                    });

                    /**
                     * Evento para ocultar a lista de tabs
                     */
                    $scope.$on('page.hideTabs', function() {
                        $element.removeClass('show-tabs');
                    });

                    /**
                     * Evento para exibir a lista de tabs
                     */
                    $scope.$on('page.showTabs', function() {
                        $element.addClass('show-tabs');
                    });
                }
            };
        }
    ]);
});

