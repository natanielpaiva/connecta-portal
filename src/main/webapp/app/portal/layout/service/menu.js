define([
    'connecta.portal'
], function (portal) {
    /**
     * Serviço de gerenciamento do menu
     * 
     * @param {type} $rootScope
     * @returns {undefined}
     */
    return portal.service('$menu', function ($rootScope, $timeout) {
        this.setMenu = function (menu) {
            $timeout(function() {
                $rootScope.$broadcast('menu.change', menu);
            });
        };
    });
});

