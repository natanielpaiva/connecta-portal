define([
    'angular'
], function (angular) {
    var portal = angular.module('connecta.portal', []);
    
    portal.value('portalDirectiveTemplate', function(template){
        return 'app/portal/directives/tpl/'.concat(template).concat('.html');
    });
    
    return portal;
});