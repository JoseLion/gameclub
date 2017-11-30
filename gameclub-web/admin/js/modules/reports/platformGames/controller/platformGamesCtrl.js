angular.module('Reports').controller('PlatformGamesCtrl', function($scope, $rootScope, getDTOptions, rest, urlParams, Const, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	rest("report/platformGames/platformGames").get(function(data) {
		$scope.platformGames = data.content;
	});

	rest("report/platformGames/totalGames").get(function(data) {
		$scope.totalGames = data;
	});

	$scope.find = function() {
		rest("report/platformGames/findPlatformGames", true).post($scope.search, function(data) {
			$scope.platformGames = data;
		});
	}
	
	$scope.clean = function() {
		$scope.search = {};
		rest("report/platformGames/platformGames").get(function(data) {
			$scope.platformGames = data.content;
		});
	}

	$scope.pageChanged = function() {
		$scope.search.page = $scope.currentPage - 1;
		$scope.find();
	}

	$scope.getExcelReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/platformGames/getExcelReport", $scope.search);
	}

	$scope.getPdfReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/platformGames/getPdfReport", $scope.search);
	}

	function setPagedData(data) {
		$scope.amountRequests = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});