angular.module('AmountRequestReport').controller('AmountRequestReportCtrl', function($scope, $rootScope, amountRequests, getDTOptions) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);
	
	amountRequests.$promise.then(function(data) {
		$scope.amountRequests = data;
	});
});