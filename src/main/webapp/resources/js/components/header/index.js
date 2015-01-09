/**
 * Componente usado para renderizar e manter o header do portal
 *
 * @param {type} angular
 * @returns {undefined}
 */
define([
    'connecta',
    'text!./tpl/index.html'
], function(connecta, template) {
    
    connecta.directive('appHeader', [
        'layoutService',
        'applicationsService',
        function(layoutService, applicationsService) {
            return {
                restrict: 'A',
                replace: true,
                template: template,
                link: function($scope, $element, attrs) {

                    // adiciona a lista de aplicações no escopo
                    $scope.applications = applicationsService.getApplications();

                    /**
                     * Oculta e exibe a barra lateral
                     *
                     * @returns {undefined}
                     */
                    $scope.toggleSidebar = function() {
                        layoutService.toggleSidebar();
                    };

                    /**
                     * Oculta e exibe a lista de páginas abertas
                     *
                     * @returns {undefined}
                     */
                    $scope.togglePageTabs = function() {
                        layoutService.togglePageTabs();
                        return false;
                    };

                    // Evento ao atualizar a aplicação em execução
                    $scope.$on('applications.change', function(event) {
                        // Adiciona os estilos personalizados da aplicação atual
                        var appInstance = applicationsService.getInstance();
                        $scope.appInstance = appInstance;

                    });
                }
            };
        }
    ]);
});

