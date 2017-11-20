angular.module("Logistic").controller('WelcomeKitModalCtrl', function($scope, $uibModalInstance, kit) {
	kit.$promise.then(function(data) {
		$scope.kit = data;
	});

	$scope.done = function() {
		$uibModalInstance.dismiss();
	}
});