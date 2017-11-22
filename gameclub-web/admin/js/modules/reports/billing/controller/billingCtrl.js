angular.module('Reports').controller('BillingCtrl', function($scope, $rootScope, getDTOptions,rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	rest("report/billing").get(function(data) {
		$scope.billingList = data.content;
	});

	rest("report/totalBilling").get(function(data) {
		$scope.totalBilling = data;
	});

});