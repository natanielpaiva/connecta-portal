define([
    'angular'
], function(angular) {

    var app = angular.module('connecta');
    return app.filter('mapFrom', function() {
        return function(input, map) {
            var out = "";
            console.log('map from',input);
            for (var a in map) {
                if (!map.hasOwnProperty(a)) {
                    continue;
                }
                if (a + '' === input + '') {
                    out = map[a];
                }
            }
            return out;
        };
    });

});
