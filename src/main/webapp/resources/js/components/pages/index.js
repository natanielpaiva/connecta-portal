/**
 * Componente responsável pela exibição das páginas das aplicações
 */
define([
    'connecta',
    'text!./tpl/index.html',
    './tabs'
], function(connecta, template) {

    connecta.directive('appPages', [
        '$compile',
        'pageService',
        function($compile, pageService) {
            var activePage = null;

            return {
                restrict: 'A',
                replace: false,
                template: template,
                link: function($scope, $element, attrs) {

                    $scope.$on('page.change', function() {
                        var page = pageService.getPage();

                        if (page) {

                            // se houver página renderizada, limpa
                            if (activePage) {
                                activePage.html.detach();
                            }

                            // seta a pagina aberta atual
                            activePage = page;

                            // página já foi renderizada antes
                            if (page.html) {
                                $('#instance', $element).empty();
                                $('#instance', $element).append(page.html);
                            } else {
                                var scope = page.scope;

                                // Ao destruir a página, verifica se está sendo
                                // exibida atualmente
                                scope.$on("$destroy", function() {
                                    if (activePage === page) {
                                        activePage.html.detach();
                                        activePage = null;
                                        pageService.back();
                                    }
                                });

                                var $template = $(page.template);
                                page.html = $compile($template)(scope);
                                $('#instance', $element).html(page.html);
                            }
                        }
                    });
                }
            };
        }
    ]);
});

