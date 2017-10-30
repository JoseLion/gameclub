angular.module("Logistic").controller('TrackingModalCtrl', function($scope, $uibModalInstance, loan, restore, kit, shippingCatalog, sweet, rest) {
	$scope.obj = {};

	if (loan != null) {
		loan.$promise.then(function(data) {
			$scope.obj = data;
		});
	}

	if (restore != null) {
		restore.$promise.then(function(data) {
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
	$scope.shippingStatus = {};

	$scope.save = function() {
		sweet.save(function() {
			let module = "";

			if (loan != null) {
				module = "loan";
			}

			if (restore != null) {
				module = "restore";
			}

			if (kit != null) {
				module = "welcomeKit";
			}

			$scope.obj.shippingStatus = $scope.shippingStatus.selected.statusTo;
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
