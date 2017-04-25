angular.module("Category").controller('ViewCategoriesCtrl', function($scope, categories, getDTOptions, $state, friendlyUrl, sweet, rest) {
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	categories.$promise.then(function(data) {
		$scope.categories = data;
	});

	$scope.addCategory = function() {
		$state.go("^.addCategory");
	}

	$scope.editCategory = function(category) {
		$state.go("^.editCategory", {id: category.id, name: friendlyUrl(category.name)});
	}

	$scope.changeStatus = function(category) {
		sweet.changeStatus(function() {
			rest("category/changeStatus/:id").get({id: category.id}, function(data) {
				let index = $scope.categories.indexOf(category);
				$scope.categories[index].status = data;

				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}
});