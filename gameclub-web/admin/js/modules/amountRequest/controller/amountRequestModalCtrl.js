angular.module("AmountRequest").controller('AmountRequestModalCtrl', function($scope, amountRequest, amountRequestCatalog, sweet, rest, Const, $uibModalInstance) {
	$scope.obj = {};
	$scope.catalog = {};
	$scope.hidden = false;

	if (amountRequest != null) {
		amountRequest.$promise.then(function(data) {
			$scope.obj = data;
			console.log($scope.obj);
			if($scope.obj.requestStatus.code == 'PGSPGD'){
				$scope.hidden = true;
			}
		});

		amountRequestCatalog.$promise.then(function(data) {
			$scope.amountRequestCatalog = data;
		});
	}

	$scope.save = function() {
		sweet.save(function() {
			rest("amountRequest/save").post($scope.obj, function(data) {
				sweet.success();
				sweet.close();
				$uibModalInstance.close(data);
			}, function(error) {
				sweet.close();
			});
		});
	}

	// shippingCatalog.$promise.then(function(data) {
	// 	$scope.shippingCatalog = data;
	// });

	// $scope.save = function() {
	// 	sweet.save(function() {
	// 		let module = "";

	// 		if (loan != null) {
	// 			module = "loan";
	// 		}

	// 		if (restore != null) {
	// 			module = "restore";
	// 		}

	// 		if (kit != null) {
	// 			module = "welcomeKit";
	// 		}

	// 		rest(module + "/save").post($scope.obj, function(data) {
	// 			sweet.success();
	// 			sweet.close();
	// 			$uibModalInstance.close(data);
	// 		}, function(error) {
	// 			sweet.close();
	// 		});
	// 	});
	// }

	$scope.cancel = function() {
		$uibModalInstance.dismiss();
	}
});