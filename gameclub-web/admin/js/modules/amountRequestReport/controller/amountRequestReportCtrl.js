angular.module('AmountRequestReport').controller('AmountRequestReportCtrl', function($scope, $rootScope, getDTOptions,rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(0);

	rest("amountRequestReport/amountRequestAll").get(function(data) {
		$scope.amountRequests = data.content;
	});
});