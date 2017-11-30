angular.module('Reports').controller('LogTrackingCtrl', function($scope, $rootScope, getDTOptions,rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	rest("report/logTracking").get(function(data) {
		$scope.logTrackings = data.content;
	});

	// rest("report/totalGames").get(function(data) {
	// 	$scope.totalGames = data;
	// });

	$scope.find = function() {
		rest("report/findLogTracking", true).post($scope.search, function(data) {
			$scope.logTrackings = data;
		});
	}
	
	$scope.clean = function() {
		$scope.search = {};
		rest("report/logTracking").get(function(data) {
			$scope.logTrackings = data.content;
		});
	}
});