angular.module('AmountRequestReport').controller('AmountRequestReportCtrl', function($scope, $rootScope, amountRequests) {

	amountRequests.$promise.then(function(data) {
		console.log(data);
		$scope.amountRequests = data;
	});
});