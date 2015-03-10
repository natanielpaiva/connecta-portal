define([
    'connecta.speaknow',
    'speaknow/interaction/service/interaction-service'
], function (speaknow) {
    return speaknow.lazy.controller('InteractionFormController', function ($scope, InteractionService, $location, $routeParams) {
        $scope.interaction = null;
        $scope.question = {};
        $scope.questions = [{answer:"Pergunta", type:"Unica resposat", itens:[{name:"a", value:"resposta a"}, {name:"b", value:"resposta b"}]}];
        $scope.contacts = [{name: "raphael", email:"raphael.vtr@gmail.com",phone:"99103322"}];
        //Variável usada para ativar e desativar os steps
        $scope.steps = [true, false, false];
        //Variável usada para ativar e desativar ícones dos steps
        $scope.stepsIcons = [true, false, false];
        //Variável com o step atual
        $scope.currentStep = 0;
        
        $scope.question.items = [
            { key:'batata', value:'frita' },
            { key:'pão', value:'de batata' }
        ];

        $scope.answersType = [{name:"Tipo 1"},{name:"Tipo 2"}];
        
        if ($routeParams.id) {
            //TOOD: Implementar update
        } 
        
        $scope.submit = function () {
            InteractionService.save($scope.interaction).then(function () {
                $location.path('speaknow/interaction');
            }, function (response) {});
        };
        
        /**
         * Método responsável por ativar e desativar os steps
         * @param {int} step: Número do step a ser atualizado
         */
        $scope.activeStep = function(step){
            $scope.steps[$scope.currentStep] = false;
            $scope.steps[step] = true;
            $scope.currentStep = step;
            
            //Deixa os ícones do steps azuis
            angular.forEach($scope.stepsIcons, function(value, key){
                if(key <= step){
                    $scope.stepsIcons[key] = true;
                } else {
                    $scope.stepsIcons[key] = false;
                }
            });
        };
        
        $scope.addQuestion = function(){
            $scope.questions.push($scope.question);
            $scope.question = null;
        };
        
        $scope.removeQuestion = function(question){
            var index = $scope.questions.indexOf(question);
            $scope.questions.splice(index, 1);
        };
        
        $scope.removeContact = function(contact){
            var index = $scope.contacts.indexOf(contact);
            $scope.contacts.splice(index, 1);
        };
        
        $scope.addContact = function(){
            $scope.contacts.push($scope.contact);
            $scope.contact = null;
        };
    });
});