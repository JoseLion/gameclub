angular.module("Logistic").controller('LoanModalCtrl', function($scope, $uibModalInstance, loan) {
	loan.$promise.then(function(data) {
		$scope.loan = data;
	});

	$scope.done = function() {
		$uibModalInstance.dismiss();
	}
});