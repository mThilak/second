'use strict';

secondApp.controller('ProductionController', function ($scope, resolvedProduction, Production) {

        $scope.productions = resolvedProduction;

        $scope.create = function () {
            Production.save($scope.production,
                function () {
                    $scope.productions = Production.query();
                    $('#saveProductionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.production = Production.get({id: id});
            $('#saveProductionModal').modal('show');
        };

        $scope.delete = function (id) {
            Production.delete({id: id},
                function () {
                    $scope.productions = Production.query();
                });
        };

        $scope.clear = function () {
            $scope.production = {name: null, year_established: null, id: null};
        };
    });
