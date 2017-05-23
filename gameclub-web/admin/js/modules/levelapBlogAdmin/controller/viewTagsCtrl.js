angular.module("LevelapBlogAdmin").controller('ViewTagsCtrl', function($scope, tags, getDTOptions, $uibModal, sweet, rest) {
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	tags.$promise.then(function(data) {
		$scope.tags = data;
	});

	$scope.manageTag = function(tag) {
		let modal = $uibModal.open({
			size: 'md',
			backdrop: 'static',
			templateUrl: 'manageTag.html',
			controller: manageTagCtrl,
			resolve: {
				tag: function() {
					return tag;
				}
			}
		});

		modal.result.then(function(data) {
			if (tag != null) {
				let index = $scope.tags.indexOf(tag);
				$scope.tags[index] = data;
			} else {
				$scope.tags.unshift(data);
			}
		});
	}

	$scope.changeStatus = function(tag) {
		sweet.changeStatus(function() {
			rest("levelapBlog/changeBlogExtraStatus/:id").get({id: tag.id}, function(data) {
				tag.status = data;
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	let manageTagCtrl = function($scope, $uibModalInstance, tag, sweet, rest) {
		$scope.blogExtra = {isTag: true};

		if (tag != null) {
			$scope.blogExtra = angular.copy(tag);
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