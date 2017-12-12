angular.module('Reports').controller('LogisticsKitsCtrl', function($scope, logisticsKits, totalShippingKidsSold, shippingKidsDelivered, welcomeKitsDelivered, getDTOptions,rest, urlParams,  $state, Const, urlRestPath) {

	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	$scope.dtColumnDefs = getDTOptions.notSortableAll(9);

	logisticsKits.$promise.then(function(data) {
		setPagedData(data);
	});

	totalShippingKidsSold.$promise.then(function(data) {
		$scope.totalShippingKidsSold = data;
	});

	shippingKidsDelivered.$promise.then(function(data) {
		$scope.shippingKidsDelivered = data;
	});

	welcomeKitsDelivered.$promise.then(function(data) {
		$scope.welcomeKitsDelivered = data;
	});

	$scope.find = function() {
		console.log("Entra");
		rest("report/logisticsKits/find").post($scope.search, function(data) {
			console.log(data);
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
		return urlParams(urlRestPath.url + "/api/report/logisticsKits/getExcelReport", $scope.search);
	}

	$scope.getPdfReportUrl = function() {
		return urlParams(urlRestPath.url + "/api/report/logisticsKits/getPdfReport", $scope.search);
	}

	function setPagedData(data) {
		$scope.logisticsKits = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});