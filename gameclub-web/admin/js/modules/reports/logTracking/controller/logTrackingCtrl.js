angular.module('Reports').controller('LogTrackingCtrl', function($scope, logTrackings, getDTOptions,rest, urlParams, Const, urlRestPath) {

	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.pagedWithButtons().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	$scope.dtColumnDefs = getDTOptions.notSortableAll(24);

	logTrackings.$promise.then(function(data) {
		setPagedData(data);
	});

	$scope.find = function() {
		rest("report/logTracking/find").post($scope.search, function(data) {
			setPagedData(data);
		});
	}
	
	$scope.clean = function() {
		$scope.search = {};
		$scope.find();
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
		$scope.logTrackings = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});