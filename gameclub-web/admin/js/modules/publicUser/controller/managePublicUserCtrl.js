angular.module("PublicUser").controller('ManagePublicUserCtrl', function($scope, publicUser, sweet, rest, $state, locations) {

	$scope.location = {};
	$scope.locationCities = [];

	publicUser.$promise.then(function(data) {
		$scope.publicUser = data;
		if($scope.publicUser.location != null) {
			$scope.location.parent = $scope.publicUser.location.parent;
			$scope.location.final = $scope.publicUser.location;
		}
	});

	locations.$promise.then(function(data) {
		$scope.locationStates = data;
	});

	$scope.findCities = function() {
		$scope.location.final = null;
		if($scope.location.parent != null) {
			rest("location/findChildrenOf/:code", true).get({code: $scope.location.parent.code}, function(data) {
				$scope.locationCities = data;
			});
		}
	};

	$scope.save = function() {
		sweet.save(function() {
			if($scope.location.final != null) {
				$scope.publicUser.location = $scope.location.final;
			} else {
				delete $scope.publicUser.location;
			}
            rest("publicUser/save").post($scope.publicUser, function() {
				sweet.success();
				sweet.close();
				$state.go("^.viewPublicUsers");
			}, function(error) {
				sweet.error(error.data.message);
			});
		});
	};

	$scope.cancel = function() {
		$state.go("^.viewPublicUsers");
	}
});
