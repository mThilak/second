'use strict';

secondApp.factory('Production', function ($resource) {
        return $resource('app/rest/productions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
