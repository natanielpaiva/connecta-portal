/**
 * Componente de wizard step
 */
define([
    'connecta',
    'text!components/wizard-steps/tpl/index.html'
], function(connecta, template) {

    return connecta.lazy.directive('wizardSteps', [
        function() {
            return {
                restrict: 'E',
                replace: true,
                template: template,
                scope: {
                    config: '='
                },
                link: function($scope, element, attrs) {
                    // adiciona a lista de aplicações no escopo
                    $scope.tabs = $scope.config.tabs || [];
                }
            };
        }
    ]);
});

