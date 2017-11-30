angular.module('Reports').controller('BillingCtrl', function($scope, billing, totalBilling, getDTOptions, Const, rest, urlRestPath, urlParams) {
	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	$scope.dtColumnDefs = getDTOptions.notSortableAll(11);

	billing.$promise.then(function(data) {
		setPagedData(data);
	});

	totalBilling.$promise.then(function(data) {
		$scope.totalBilling = data;
	});

	$scope.find = function() {
		rest("report/billing/find").post($scope.search, function(data) {
			setPagedData(data);
		});
	}

	$scope.clear = function() {
		$scope.search = {};
		$scope.find();
	}

	$scope.pageChanged = function() {
		$scope.search.page = $scope.currentPage - 1;
		$scope.find();
	}

	$scope.getExcelReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/billing/getExcelReport", $scope.search);
	}

	$scope.getPdfReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/billing/getPdfReport", $scope.search);
	}

	function setPagedData(data) {
		$scope.billing = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});