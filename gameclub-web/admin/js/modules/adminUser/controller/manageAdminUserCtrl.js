angular.module("AdminUser").controller('ManageAdminUserCtrl', function($scope, profiles, adminUser, sweet, rest, $state) {
	$scope.adminUser = {};

	profiles.$promise.then(function(data) {
		$scope.profiles = data;

		if (adminUser != null) {
			adminUser.$promise.then(function(s) {
				$scope.adminUser = userData;
			});
		}
	});

	$scope.save = function() {
		sweet.save(function() {
			rest("adminUser/save").post($scope.adminUser, function() {
				sweet.success();
				sweet.close();
				$state.go("^.viewAdminUsers");
			}, function(error) {
				sweet.error(error.data.message);
			});
		});
	}

	$scope.cancel = function() {
		$state.go("^.viewAdminUsers");
	}
});