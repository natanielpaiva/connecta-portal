define([
    'connecta.portal'
], function(portal){
    return portal.directive('keyValueItem', function(){
        return {
            templateUrl: "app/portal/layout/directive/template/key-value-item.html",
            require: "^keyValue",
            transclude: true
        };
    });
});