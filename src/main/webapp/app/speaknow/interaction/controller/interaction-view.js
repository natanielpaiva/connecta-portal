define([
    'connecta.speaknow',
    'speaknow/interaction/service/interaction-service'
], function (speaknow) {
    return speaknow.lazy.controller('InteractionViewController', function (
            $scope, InteractionService, $routeParams, $location, ngTableParams
            ) {

        redirectToInteraction = function () {
            $location.path('speaknow/interaction');
        };

        if ($routeParams.id) {
            InteractionService.get($routeParams.id).success(function (data) {
                $scope.interaction = data;
            });
        } else {
            console.error("Id da interaction não informado na url");
            redirectToInteraction();
        }

        //TODO Vai ter search na view da Interaction pras actions?
        $scope.search = {
            name: ''
        };
        $scope.tableParams = new ngTableParams({
            count: 20,
            page: 1,
            filter: $scope.search
        }, {
            getData: function ($defer, params) {
                var data = $scope.interaction.actions;
                return $defer.resolve(data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            },
            counts: [20, 50, 100]
        });

        //TODO caixa de dialogo de confirmação do delete
        $scope.deleteInteraction = function (id) {
            InteractionService.delete(id).success(function () {
                console.info("Interaction Deletada com sucesso!");
                redirectToInteraction();
            });
        };

        $scope.editInteraction = function (id) {
            $location.path('speaknow/interaction/' + id);
        };

    });
});