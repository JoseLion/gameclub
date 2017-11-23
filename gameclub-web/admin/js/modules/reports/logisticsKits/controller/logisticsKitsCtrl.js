angular.module('Reports').controller('LogisticsKitsCtrl', function($scope, $rootScope, getDTOptions,rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	rest("report/logisticsKits").get(function(data) {
		$scope.logisticsKits = data.content;
	});

	rest("report/totalShippingKidsSold").get(function(data) {
		$scope.totalShippingKidsSold = data;
	});
	
	rest("report/shippingKidsDelivered").get(function(data) {
		$scope.shippingKidsDelivered = data;
	});
	
	rest("report/welcomeKitsDelivered").get(function(data) {
		$scope.welcomeKitsDelivered = data;
	});
});