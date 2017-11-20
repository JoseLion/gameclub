angular.module("Console").controller('ManageConsoleCtrl', function($scope, consl, sweet, rest, $state) {
	$scope.consl = {};
	$scope.images = {};

	if (consl != null) {
		consl.$promise.then(function(data) {
			$scope.consl = data;
		});
	}

	$scope.save = function() {
		sweet.save(function() {
			let formData = {
				console: $scope.consl,
				whiteLogo: $scope.images.white,
				blackLogo: $scope.images.black
			};

			rest("console/save").multipart(formData, function() {
				sweet.success();
				sweet.close();
				$scope.cancel();
			}, function(error) {
				sweet.error(error.data.message);
			});
		});
	}

	$scope.cancel = function() {
		$state.go("^.viewConsoles");
	}
});