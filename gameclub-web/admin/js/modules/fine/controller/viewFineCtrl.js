angular.module("Fine").controller('ViewFineCtrl',function($scope, $rootScope, fines, getDTOptions, $state, friendlyUrl, sweet, rest){
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);
	
	fines.$promise.then(function(data){
		$scope.fines = data;
	});	

	$scope.executeFine = function(data){
		console.log(data.owner.shownBalance);
		console.log(data.owner);
		sweet.default("Se actualizará los datos ingresado", function() {
			rest("fine/saveBalanceFine").post(	data, function() {
				sweet.success();
				sweet.close();
			}, function(error) {
				sweet.error(error.data ? error.data.message : error);
			});
		});
	}

	$scope.noExecuteFine = function(data){
		sweet.default("Se actualizará los datos ingresado", function() {
			rest("fine/notApplyFine").post(	data, function() {
				sweet.success();
				sweet.close();
			}, function(error) {
				sweet.error(error.data ? error.data.message : error);
			});
		});
	}
	// $scope.data = {
 //    model: null,
 //    availableOptions: [
 //      {name: 'PENDIENTE'},
 //      {name: 'APLICA'},
 //      {name: 'CANCELADO'},
 //      {name: 'PAGADO'}
 //    ]
 //   };

  //  if(fines.length > 0){
  //  		for (fine of fines) {
		//     if(fine.apply==null || fine.apply==true){
		//     	console.log(fine.description);
		//     }
		// }
  //  }
  		
})