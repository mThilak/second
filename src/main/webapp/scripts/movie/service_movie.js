'use strict';

secondApp.factory('Movie', function ($resource) {
        return $resource('app/rest/movies/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
