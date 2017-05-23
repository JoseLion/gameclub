angular.module("LevelapBlogAdmin").controller('ViewCategoriesCtrl', function($scope, categories, getDTOptions, $uibModal, sweet, rest) {
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	categories.$promise.then(function(data) {
		$scope.categories = data;
	});

	$scope.manageCategory = function(category) {
		let modal = $uibModal.open({
			size: 'md',
			backdrop: 'static',
			templateUrl: 'manageCategory.html',
			controller: manageCategoryCtrl,
			resolve: {
				category: function() {
					return category;
				}
			}
		});

		modal.result.then(function(data) {
			if (category != null) {
				let index = $scope.categories.indexOf(category);
				$scope.categories[index] = data;
			} else {
				$scope.categories.unshift(data);
			}
		});
	}

	$scope.changeStatus = function(category) {
		sweet.changeStatus(function() {
			rest("levelapBlog/changeBlogExtraStatus/:id").get({id: category.id}, function(data) {
				category.status = data;
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	let manageCategoryCtrl = function($scope, $uibModalInstance, category, sweet, rest) {
		$scope.blogExtra = {};

		if (category != null) {
			$scope.blogExtra = angular.copy(category);
		}

		$scope.cancel = function() {
			$uibModalInstance.dismiss();
		}

		$scope.save = function() {
			sweet.save(function() {
				rest("levelapBlog/saveBlogExtra").post($scope.blogExtra, function(data) {
					sweet.success();
					sweet.close();
					$uibModalInstance.close(data);
				}, function(error) {
					sweet.error(error.data != null ? error.data.message : error);
				});
			});
		}
	}
});