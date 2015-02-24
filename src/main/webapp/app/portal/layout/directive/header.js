define([
    'connecta.portal'
], function(portal) {
    /**
     * Componente usado para renderizar e manter o header do portal
     */
    return portal.directive('appHeader', function(layoutService, applicationsService) {
        return {
            restrict: 'A',
            replace: true,
            templateUrl: 'app/portal/layout/directive/template/header.html',
            link: function(scope) {

                // adiciona a lista de aplicações no escopo
                scope.applications = applicationsService.getApplications();

                /**
                 * Oculta e exibe a barra lateral
                 *
                 * @returns {undefined}
                 */
                scope.toggleSidebar = function() {
                    layoutService.toggleSidebar();
                };

                /**
                 * Oculta e exibe a lista de páginas abertas
                 *
                 * @returns {undefined}
                 */
                scope.togglePageTabs = function() {
                    layoutService.togglePageTabs();
                    return false;
                };

                // Evento ao atualizar a aplicação em execução
                scope.$on('applications.change', function(event) {
                    // Adiciona os estilos personalizados da aplicação atual
                    var appInstance = applicationsService.getInstance();
                    scope.appInstance = appInstance;
                });
            }
        };
    });
});

