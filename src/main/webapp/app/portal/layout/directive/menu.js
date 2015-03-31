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
            controller: function ($scope, $location) {
                /**
                 * Testa se esse menu é o ativo atualmente
                 * @param {type} item
                 * @returns {Boolean}
                 */
                $scope.isActive = function (item) {
                    var active = false;
                    if (item.href) {
                        // Monta uma RegExp e testa contra a url atual removendo a primeira barra
                        active = new RegExp(item.href).test($location.url().replace('/', ''));
                    }

                    if (item.children && item.children.length) {
                        console.log(item.title, 'has children');
                        console.log('active flag was: ', active);
                        active = active && item.children.filter(function(child) {
                            console.log('found an active child: ', child.title);
                            return $scope.isActive(child);
                        }).length > 0;
                        console.log('active flag is now: ', active);
                    }

                    return active;
                };

                $scope.toggle = function (item) {
                    if (item.children && item.children.length) {
                        console.log('trying to open');
                        item.opened = !item.opened;
                    }
                };

                $scope.isOpened = function (item) {
                    return item.opened || $scope.isActive(item);
                };

                $scope.menu = [
                    {
                        href: 'presenter/analysis',
                        title: 'ANALYSIS.ANALYSIS',
                        icon: 'icon-analysis',
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
                        icon: 'icon-viewer',
                        children: []
                    },
                    {
                        href: 'presenter/network',
                        title: 'NETWORK.NETWORKS',
                        icon: 'icon-networks',
                        children: []
                    }
                ];

                $scope.$on('menu.change', function (newMenu) {
                    $scope.menu = newMenu;
                });
            }
        };
    });
});
