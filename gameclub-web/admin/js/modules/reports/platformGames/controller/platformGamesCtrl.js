angular.module('Reports').controller('PlatformGamesCtrl', function($scope, $rootScope, getDTOptions,rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	rest("report/platformGames").get(function(data) {
		$scope.platformGames = data.content;
	});

	rest("report/totalGames").get(function(data) {
		$scope.totalGames = data;
	});

	$scope.find = function() {
		rest("report/findPlatformGames", true).post($scope.search, function(data) {
			$scope.platformGames = data;
		});
	}
	
	$scope.clean = function() {
		$scope.search = {};
		rest("report/platformGames").get(function(data) {
			$scope.platformGames = data.content;
		});
	}
});