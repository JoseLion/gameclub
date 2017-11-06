angular.module("AmountRequest").controller('AmountRequestModalCtrl', function($scope, amountRequest, amountRequestCatalog, sweet, rest, Const, $uibModalInstance) {
	$scope.amtRequest = {};
	$scope.catalog = {};
	$scope.hidden = false;
	$scope.disable = false;

	if (amountRequest != null) {
		amountRequest.$promise.then(function(data) {
			$scope.amtRequest = data;
			console.log($scope.amtRequest);
			if($scope.amtRequest.requestStatus.code == 'PGSPGD'){
				$scope.hidden = true;				
			}
			if($scope.amtRequest.billPhoto == null){
				$scope.disable = true;
			}
		});

		amountRequestCatalog.$promise.then(function(data) {
			$scope.amountRequestCatalog = data;
		});
	}

	$scope.save = function() {
		sweet.save(function() {
			rest("amountRequest/save").post($scope.amtRequest, function(data) {
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