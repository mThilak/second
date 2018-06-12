'use strict';

secondApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/movie', {
                    templateUrl: 'views/movies.html',
                    controller: 'MovieController',
                    resolve:{
                        resolvedMovie: ['Movie', function (Movie) {
                            return Movie.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
