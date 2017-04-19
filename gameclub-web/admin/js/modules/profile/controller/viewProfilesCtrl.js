angular.module("Profile").controller('ViewProfilesCtrl', function($scope, profiles, getDTOptions, $state, friendlyUrl, sweet, rest) {
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	profiles.$promise.then(function(data) {
		$scope.profiles = data;
	});

	$scope.addProfile = function() {
		$state.go("^.addProfile");
	}

	$scope.editProfile = function(profile) {
		$state.go("^.editProfile", {name: friendlyUrl(profile.name), id: profile.id});
	}

	$scope.changeStatus = function(profile) {
		sweet.changeStatus(function() {
			rest("profile/changeStatus/:id").get({id: profile.id}, function(data) {
				let index = $scope.profiles.indexOf(profile);
				$scope.profiles[index].status = data;
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}
});