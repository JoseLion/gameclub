angular.module('Reports').controller('LogisticsKitsCtrl', function($scope, $rootScope, getDTOptions,rest, urlParams,  $state, Const, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);
	$scope.amountRequests = {};

	rest("report/logisticsKits/logisticsKits").get(function(data) {
		$scope.logisticsKits = data.content;
	});

	rest("report/logisticsKits/totalShippingKidsSold").get(function(data) {
		$scope.totalShippingKidsSold = data;
	});
	
	rest("report/logisticsKits/shippingKidsDelivered").get(function(data) {
		$scope.shippingKidsDelivered = data;
	});
	
	rest("report/logisticsKits/welcomeKitsDelivered").get(function(data) {
		$scope.welcomeKitsDelivered = data;
	});

	$scope.find = function() {
		rest("report/logisticsKits/findLogisticsKits", true).post($scope.search, function(data) {
			$scope.logisticsKits = data;
		});
	}
	
	$scope.clean = function() {
		$scope.search = {};
		rest("report/logisticsKits/logisticsKits").get(function(data) {
			$scope.logisticsKits = data.content;
		});
	}

	$scope.pageChanged = function() {
		$scope.search.page = $scope.currentPage - 1;
		$scope.find();
	}

	$scope.getExcelReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/logisticsKits/getExcelReport", $scope.search);
	}

	$scope.getPdfReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/logisticsKits/getPdfReport", $scope.search);
	}

	function setPagedData(data) {
		$scope.amountRequests = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});