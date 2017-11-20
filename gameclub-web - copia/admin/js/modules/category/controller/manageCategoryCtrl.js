angular.module("Category").controller('ManageCategoryCtrl', function($scope, category, sweet, rest, $state) {
	$scope.category = {};

	if (category != null) {
		category.$promise.then(function(data) {
			$scope.category = data;
		});
	}

	$scope.save = function() {
		sweet.save(function() {
			let formData = {
				category: $scope.category,
				whiteVector: $scope.whiteImage,
				blackVector: $scope.blackImage
			};

			rest("category/save").multipart(formData, function() {
				sweet.success();
				sweet.close();
				$scope.cancel();
			}, function(error) {
				sweet.error(error.data.message);
			});
		});
	}

	$scope.cancel = function() {
		$state.go("^.viewCategories");
	}
});