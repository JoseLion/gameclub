angular.module('Reports').controller('RegisteredUsersCtrl', function($scope, registeredUsers, totalUsers, rest, getDTOptions, urlParams, Const, urlRestPath) {
	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	$scope.dtColumnDefs = getDTOptions.notSortableAll(12);

	registeredUsers.$promise.then(function(data) {
		setPagedData(data);
	});

	totalUsers.$promise.then(function(data) {
		$scope.totalUsers = data;
	});

	$scope.find = function() {
		rest("report/registeredUsers/find").post($scope.search, function(data) {
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
		return urlParams(urlRestPath.url + "/api/report/registeredUsers/getExcelReport", $scope.search);
	}

	$scope.getPdfReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/registeredUsers/getPdfReport", $scope.search);
	}

	function setPagedData(data) {
		$scope.registeredUsers = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});