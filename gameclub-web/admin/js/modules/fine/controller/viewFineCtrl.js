angular.module("Fine").controller('ViewFineCtrl',function($scope, fines, getDTOptions, $state, friendlyUrl, sweet, rest){
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);
	
	fines.$promise.then(function(data){
		$scope.fines = data;
	});	

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