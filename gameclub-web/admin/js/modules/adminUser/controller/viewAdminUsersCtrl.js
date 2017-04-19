angular.module("AdminUser").controller('ViewAdminUsersCtrl', function($scope, adminUsers, profiles, getDTOptions, rest, $state, friendlyUrl, sweet, rest, notif) {
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

	$scope.editUser = function(user) {
		$state.go("^.editAdminUser", {fullName: friendlyUrl(user.fullName), id: user.id});
	}

	$scope.changeStatus = function(user) {
		sweet.changeStatus(function() {
			rest("adminUser/changeStatus/:id").get({id: user.id}, function(data) {
				let index = $scope.adminUsers.indexOf(user);
				$scope.adminUsers[index].status = data;
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	$scope.resetPassword = function(id) {
		sweet.default("Se reestablecerá la contraseña del usuario y se le enviará un correo con una contraseña temporal", function() {
			rest("adminUser/resetPassword/:id").get({id: id}, function() {
				notif.success("La contraseña se reestableció con éxito");
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}
});