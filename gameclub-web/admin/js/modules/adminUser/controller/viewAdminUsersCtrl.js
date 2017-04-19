angular.module("AdminUser").controller('ViewAdminUsersCtrl', function($scope, adminUsers, profiles, getDTOptions, rest, $state) {
	$scope.search = {};
	$scope.dtOptions = getDTOptions.unpaged();
	$scope.dtColumnDefs = getDTOptions.notSortableAll(5);

	adminUsers.$promise.then(function(data) {
		$scope.adminUsers = data;
	});

	profiles.$promise.then(function(data) {
		$scope.profiles = data;
	});

	$scope.find = function() {
		if ($scope.search.profile != null) {
			$scope.search.profileId = $scope.search.profile.id;
		}

		rest("adminUser/findAdminUsers", true).post($scope.search, function(data) {
			$scope.adminUsers = data;
		});
	}

	$scope.clear = function() {
		$scope.search = {};
		$scope.find();
	}

	$scope.addAdminUser = function() {
		$state.go("^.addAdminUser");
	}
});