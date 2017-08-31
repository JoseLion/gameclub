angular.module("Logistic").controller('RestoreModalCtrl', function($scope, $uibModalInstance, restore) {
	restore.$promise.then(function(data) {
		$scope.restore = data;
	});

	$scope.done = function() {
		$uibModalInstance.dismiss();
	}
});