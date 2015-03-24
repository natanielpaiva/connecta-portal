define([
    'connecta.portal'
], function (portal) {
    /**
     * Componente usado para renderizar e manter o menu
     * @param {type} applicationsService
     */
    return portal.directive('menu', function (applicationsService) {
        return {
            replace: true,
            templateUrl: 'app/portal/layout/directive/template/menu.html',
            controller: function ($scope) {
                $scope.menu = [
                    {
                        href: 'presenter/analysis',
                        title: 'ANALYSIS.ANALYSIS',
                        icon: 'icon-bar-graph icon-invert-horizontal',
                        children: []
                    },
                    {
                        title: 'DATASOURCE.DATASOURCE',
                        icon: 'icon-database2',
                        children: [
                            {
                                href: 'presenter/datasource',
                                title: 'DATASOURCE.ANALYSIS_SOURCE'
                            },
                            {
                                href: 'presenter/singlesource',
                                title: 'SINGLESOURCE.MEDIA'
                            }
                        ]
                    }
                ];

                $scope.$on('applications.change', function () {
                    var appInstance = applicationsService.getInstance();
                    if (appInstance) {
                        $scope.menu = appInstance.navigation;
                    }
                });
            }
        };
    });
});
