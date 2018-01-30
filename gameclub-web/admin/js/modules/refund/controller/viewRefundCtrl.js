angular.module("Refund").controller('ViewRefundCtrl',function($scope, $rootScope, refunds, getDTOptions, $state, friendlyUrl, sweet, rest){

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	refunds.$promise.then(function(data){
		$scope.refunds = data;
	});
});