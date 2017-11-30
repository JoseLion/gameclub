angular.module('Reports').controller('LogTrackingCtrl', function($scope, $rootScope, getDTOptions,rest, urlParams, Const, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	rest("report/logTracking/logTracking").get(function(data) {
		$scope.logTrackings = data.content;
	});

	// rest("report/totalGames").get(function(data) {
	// 	$scope.totalGames = data;
	// });

	$scope.find = function() {
		rest("report/logTracking/findLogTracking", true).post($scope.search, function(data) {
			$scope.logTrackings = data;
		});
	}
	
	$scope.clean = function() {
		$scope.search = {};
		rest("report/logTracking/logTracking").get(function(data) {
			$scope.logTrackings = data.content;
		});
	}

	$scope.pageChanged = function() {
		$scope.search.page = $scope.currentPage - 1;
		$scope.find();
	}

	$scope.getExcelReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/logTracking/getExcelReport", $scope.search);
	}

	$scope.getPdfReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/logTracking/getPdfReport", $scope.search);
	}

	function setPagedData(data) {
		$scope.amountRequests = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});