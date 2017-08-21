angular.module("Logistic").controller('TrackingModalCtrl', function($scope, $uibModalInstance, kit, shippingCatalog, sweet, rest) {
	$scope.kit = {};

	kit.$promise.then(function(data) {
		$scope.kit = data;
	});

	shippingCatalog.$promise.then(function(data) {
		$scope.shippingCatalog = data;
	});

	$scope.save = function() {
		sweet.save(function() {
			rest("welcomeKit/save").post($scope.kit, function(data) {
				sweet.success();
				sweet.close();
				$uibModalInstance.close(data);
			}, function(error) {
				sweet.close();
			});
		});
	}

	$scope.cancel = function() {
		$uibModalInstance.dismiss();
	}
});