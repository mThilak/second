'use strict';

secondApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/production', {
                    templateUrl: 'views/productions.html',
                    controller: 'ProductionController',
                    resolve:{
                        resolvedProduction: ['Production', function (Production) {
                            return Production.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
