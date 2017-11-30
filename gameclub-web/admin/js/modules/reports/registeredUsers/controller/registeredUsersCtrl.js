angular.module('Reports').controller('RegisteredUsersCtrl', function($scope, $rootScope, rest, getDTOptions,urlParams, Const, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);
	// $scope.search = {};
	// $scope.totalElements;
	// $scope.beginning;
	// $scope.end;

	// $scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
	// 	return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	// });

	// $scope.dtColumnDefs = getDTOptions.notSortableAll(6);

	rest("report/registeredUsers/registeredUsersAll").get(function(data) {
		$scope.registeredUsersAll = data.content;
	});

	rest("report/registeredUsers/totalUsers").get(function(data) {
		$scope.totalUsers = data;
	});

	$scope.find = function() {
		rest("report/registeredUsers/findRegisteredUsers", true).post($scope.search, function(data) {
			$scope.registeredUsersAll = data;
		});
	}
	
	$scope.clean = function() {
		$scope.search = {};
		rest("report/registeredUsers/registeredUsersAll").get(function(data) {
			$scope.registeredUsersAll = data.content;
		});
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
		$scope.amountRequests = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});