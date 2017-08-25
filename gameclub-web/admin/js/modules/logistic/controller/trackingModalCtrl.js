angular.module("Logistic").controller('TrackingModalCtrl', function($scope, $uibModalInstance, loan, kit, shippingCatalog, sweet, rest) {
	$scope.obj = {};

	if (loan != null) {
		loan.$promise.then(function(data) {
			$scope.obj = data;
		});
	}

	if (kit != null) {
		kit.$promise.then(function(data) {
			$scope.obj = data;
		});
	}

	shippingCatalog.$promise.then(function(data) {
		$scope.shippingCatalog = data;
	});

	$scope.save = function() {
		sweet.save(function() {
			let module = "";

			if (loan != null) {
				module = "loan";
			}

			if (kit != null) {
				module = "welcomeKit";
			}

			rest(module + "/save").post($scope.obj, function(data) {
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