angular.module("Fine").controller('ViewFineCtrl',function($scope, $rootScope, fines, getDTOptions, $state, friendlyUrl, sweet, rest){
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	fines.$promise.then(function(data){
		$scope.fines = data;
	});

	$scope.executeFine = function(data){
		sweet.default("Se actualizará los datos ingresado", function() {
			rest("fine/applyFine").post(data, function(response) {
				let index = $scope.fines.indexOf(data);
				$scope.fines[index] = response;
				sweet.success();
				sweet.close();
			}, function(error) {
				sweet.error(error.data ? error.data.message : error);
			});
		});
	}

	$scope.noExecuteFine = function(data){
		sweet.default("Se actualizará los datos ingresado", function() {
			rest("fine/notApplyFine").post(	data, function(response) {
				let index = $scope.fines.indexOf(data);
				$scope.fines[index] = response;
				sweet.success();
				sweet.close();
			}, function(error) {
				sweet.error(error.data ? error.data.message : error);
			});
		});
	}

})
