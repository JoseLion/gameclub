angular.module('RegisteredUsers').controller('RegisteredUsersCtrl', function($scope, $rootScope, registeredUsersAll, getDTOptions) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);
	
	registeredUsersAll.$promise.then(function(data) {
		$scope.registeredUsersAll = data;
	});
});