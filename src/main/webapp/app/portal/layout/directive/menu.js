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
                                href: 'presenter/group',
                                title: 'GROUP.GROUPS'
                            },
                            {
                                href: 'presenter/hierarchy',
                                title: 'HIERARCHY.HIERARCHY_LIST'
                            },
                            {
                                href: 'presenter/singlesource',
                                title: 'SINGLESOURCE.MEDIA'
                            }
                        ]
                    },
                    {
                        href: 'presenter/viewer',
                        title: 'VIEWER.VIEWERS',
                        icon: 'icon-blackboard',
                        children: []
                    },
                    {
                        href: 'presenter/network',
                        title: 'NETWORK.NETWORKS',
                        icon: 'icon-flow-tree',
                        children: []
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
