angular.module('AmountRequestReport').controller('AmountRequestReportCtrl', function($scope, $rootScope, amountRequests, getDTOptions, Const, rest, urlRestPath) {
	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	$scope.dtColumnDefs = getDTOptions.notSortableAll(6);

	amountRequests.$promise.then(function(data) {
		setPagedData(data);
	});

	$scope.find = function() {
		rest("report/amountRequest/find").post($scope.search, function(data) {
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
		let url = urlRestPath.url + "/api/report/amountRequest/getExcelReport?";

		return url;
	}

	function setPagedData(data) {
		$scope.amountRequests = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});