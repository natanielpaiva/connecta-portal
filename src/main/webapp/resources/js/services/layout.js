/**
 * Serviço de gerenciamento do layout
 *
 * Aqui ficam as funcionalidades compartilhadas do layout
 *
 * @param {type} angular
 * @returns {undefined}
 */
define([
    'angular'
], function(angular) {

    var app = angular.module('connecta');
    app.service('layoutService', [
        '$rootScope',
        function($rootScope) {

            var isShowSidebar = true;
            var isShowPageTabs = false;
            var service = {
                /**
                 * Oculta e exibe a barra lateral (menu esquerdo)
                 *
                 * @returns {undefined}
                 */
                toggleSidebar: function() {
                    isShowSidebar = !isShowSidebar;
                    if (isShowSidebar) {
                        $rootScope.$broadcast('sidebar.show');

                        // oculta a lista de área de trabalho
                        if(isShowPageTabs){
                            service.togglePageTabs();
                        }
                    } else {
                        $rootScope.$broadcast('sidebar.hide');
                    }
                },
                /**
                 * Oculta e exibe a lista de páginas abertas
                 *
                 * @returns {undefined}
                 */
                togglePageTabs: function() {
                    isShowPageTabs = !isShowPageTabs;
                    if (isShowPageTabs) {
                        $rootScope.$broadcast('page.showTabs');

                        // oculta o menu lateral
                        if(isShowSidebar){
                            service.toggleSidebar();
                        }
                    } else {
                        $rootScope.$broadcast('page.hideTabs');
                    }
                }
            };
            return service;
        }]);
});

