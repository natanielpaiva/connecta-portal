define([
    'connecta.speaknow',
    'speaknow/action/service/action-service',
    'speaknow/interaction/service/interaction-service',
    'speaknow/action/controller/action-modal'
], function (speaknow) {
    /* //TODO
     * tirar bind qnd editar
     * validar parametro antes de adicionar (param e section)
     * regras para adicionar (substituir ou negar adição)
     */
    return speaknow.lazy.controller('ActionFormController', function ($scope,
            InteractionService, ActionService, $location, $routeParams, $modal) {

        $scope.interaction = ActionService.getInteraction();

        $scope.action = {
            steps: [],
            icon: "dump"
        };

        $scope.step = {
            sections: [],
            name: "Step One",
            type: "POLL"
        };

        $scope.action.steps.push($scope.step);

        $scope.section = {
            params: []
        };

        $scope.param = {
            options: []
        };

        $scope.icons = [];
        //Recupera a lista de icones do selection.json
        ActionService.getIcons().success(function (data) {
            $scope.icons = data.icons.slice(0, 100);
        });

        $scope.isEditing = false;

        if ($routeParams.id) {
            $scope.isEditing = true;
            ActionService.get($routeParams.id).success(function (data) {
                $scope.action = data;
                $scope.step = data.steps[0];
                console.log($scope.action);
                $scope.interaction = data.interaction;
            });
        }

        // Executa a modal para escolha de ícones
        $scope.openIconModal = function () {
            var modalIcon = $modal.open({
                templateUrl: 'app/speaknow/action/template/modal.html',
                controller: 'ActionFormModalController',
                windowClass: 'connecta-modal',
                resolve: {
                    items: function () {
                        return $scope.icons;
                    },
                    selected: function () {
                        return $scope.action.icon;
                    }
                }
            });

            modalIcon.result.then(function (selectedItem) {
                $scope.action.icon = selectedItem;
            });
        };

        // Recupera os tipo de interações disponíveis (enum InteractionType)
        $scope.actionTypes = [];
        ActionService.getActionTypes().then(function (response) {
            $scope.actionTypes = response.data;
            $scope.action.type = $scope.actionTypes[0];
        });

        // Recupera os tipos de parametros (enum InteractionParameterType)
        $scope.paramTypes = [];
        ActionService.getParamTypes().then(function (response) {
            $scope.paramTypes = response.data;
            $scope.param.type = $scope.paramTypes[0];
        });


        $scope.addSection = function () {
            var hasSection = false;
            for (var key in $scope.step.sections) {
                var value = $scope.step.sections[key];
                if ($scope.section.title === value.title) {
                    value = $scope.section;
                    hasSection = true;
                    break;
                }
            }
            if (!hasSection) {
                $scope.step.sections.push($scope.section);
            }

            $scope.section = {
                params: []
            };
        };

        $scope.removeSection = function (section) {
            $scope.step.sections.splice($scope.step.sections.indexOf(section), 1);
        };

        $scope.editSection = function (section) {
            $scope.section = section;
            $scope.param = {
                type: $scope.paramTypes[0],
                options: []
            };
        };

        $scope.addParam = function () {
            var hasParam = false;
            for (var key in $scope.section.params) {
                var value = $scope.section.params[key];
                if (value.title === $scope.param.title) {
                    hasParam = true;
                    value = $scope.param;
                }
            }
            if (!hasParam) {
                $scope.section.params.push($scope.param);
            }

            $scope.param = {
                type: $scope.paramTypes[0],
                options: []
            };
        };

        $scope.removeParam = function (param) {
            $scope.section.params.splice($scope.section.params.indexOf(param), 1);
        };

        $scope.editParam = function (param) {
            $scope.param = param;
        };

        //Função de change para mostrar lista de adição de options dependendo
        //do tipo de parametro que for escolhido
        var paramOptsTypes = ["MULTI_SELECT", "MULTI_CHECKBOX", "RADIO"];
        $scope.showParamOpts = false;

        $scope.$watch('param.type', function (newValue, oldValue) {
            $scope.showParamOpts = paramOptsTypes.indexOf(newValue) > -1;
        });

        $scope.option = "";

        $scope.addParamOpt = function () {
            var index = $scope.param.options.indexOf($scope.option);
            if (index > -1) {
                $scope.param.options[index] = $scope.option;
            } else {
                $scope.param.options.push($scope.option);
            }
            $scope.option = "";
        };

        $scope.removeParamOption = function (option) {
            $scope.param.options.splice($scope.param.options.indexOf(option), 1);
        };

        $scope.submit = function () {
            if (!$scope.isEditing) {
                $scope.interaction.actions = [$scope.action];
                $scope.interaction.company = {id: 1};

                InteractionService.save($scope.interaction).success(function (response) {
                    ActionService.clearInteraction();
                    $location.path('/speaknow/interaction');
                });
            } else {
                ActionService.save($scope.action).success(function(response){
                    ActionService.clearInteraction();
                    $location.path('/speaknow/interaction/' + $scope.action.interaction.id);
                });
            }
        };

    });
});