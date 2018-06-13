'use strict';

secondApp.controller('MovieController', function ($scope, resolvedMovie, Movie) {

        $scope.movies = resolvedMovie;

        $scope.create = function () {
            Movie.save($scope.movie,
                function () {
                    $scope.movies = Movie.query();
                    $('#saveMovieModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.movie = Movie.get({id: id});
            $('#saveMovieModal').modal('show');
        };
        
        $scope.update = function (id) {
        
        	$('#viewMovieModal').modal('hide');
            $scope.movie = Movie.get({id: id});
            $('#saveMovieModal').modal('show');
        };

		$scope.view = function (id) {
            $scope.movie = Movie.get({id: id});
            $('#viewMovieModal').modal('show');
        };
        
        $scope.delete = function (id) {
            Movie.delete({id: id},
                function () {
                    $scope.movies = Movie.query();
                });
        };

        $scope.clear = function () {
            $scope.movie = {name: null, budget: null, id: null};
        };
    });
