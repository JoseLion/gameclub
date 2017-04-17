angular.module("Profile").controller('ViewProfilesCtrl', function($scope, profiles, getDTOptions, $state) {
	$scope.dtOptions = getDTOptions.unpaged();
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	profiles.$promise.then(function(data) {
		$scope.profiles = data;
	});

	$scope.addProfile = function() {
		$state.go("^.addProfile");
	}
});