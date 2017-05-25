angular.module("LevelapBlogAdmin").controller('ManageArticleCtrl', function($scope, $rootScope, article, categories, tags, $state, $uibModal, sweet, rest, getImageBase64) {
	$scope.article = {};
	setAuthor();

	$scope.tinymceOptions = {
		onChange: function(e) {},
		inline: false,
		plugins: 'advlist autolink link image lists charmap print preview',
		skin: 'lightgray',
		theme: 'modern',
		height: 330,
		language: 'es_MX'
	};

	if (article != null) {
		article.$promise.then(function(data) {
			console.log("data: ", data);
			$scope.article = data;
		});
	}

	if (categories != null) {
		categories.$promise.then(function(data) {
			$scope.categories = data;
		});
	}

	if (tags != null) {
		tags.$promise.then(function(data) {
			$scope.tags = data;
		});
	}

	$scope.$watch("banner", function(newValue, oldValue) {
		if (newValue != null) {
			let reader = new FileReader();
			reader.onload = function() {
				$scope.bannerBase64 = getImageBase64(reader.result, newValue.type);
			};

			reader.readAsArrayBuffer(newValue);
		}
		console.log("newValue: ", newValue);
	});

	$scope.cancel = function() {
		$state.go("^.viewArticles");
	}

	$scope.addCategory = function() {
		let modal = $uibModal.open({
			size: "sm",
			backdrop: true,
			templateUrl: 'addExtraModal.html',
			controller: AddExtraModalCtrl,
			resolve: {
				isTag: function() {
					return false;
				}
			}
		});

		modal.result.then(function(blogExtra) {
			let index = -1;

			if ($scope.categories == null) {
				$scope.categories = [];
			}

			for (let i = $scope.categories.length - 1; i >= 0; i--) {
				if ($scope.categories[i].text == blogExtra.text) {
					index = i;
					break;
				}
			}

			if (index < 0) {
				$scope.categories.push(blogExtra);
				$scope.article.category = $scope.categories[$scope.categories.length-1];
			} else {
				$scope.article.category = $scope.categories[index];
			}
		});
	}

	$scope.addTag = function() {
		let modal = $uibModal.open({
			size: "sm",
			backdrop: true,
			templateUrl: 'addExtraModal.html',
			controller: AddExtraModalCtrl,
			resolve: {
				isTag: function() {
					return true;
				}
			}
		});

		modal.result.then(function(blogExtra) {
			let index = -1;

			if ($scope.tags == null) {
				$scope.tags = [];
			}

			if ($scope.article.tags == null) {
				$scope.article.tags = [];
			}

			for (let i = $scope.tags.length - 1; i >= 0; i--) {
				if ($scope.tags[i].text == blogExtra.text) {
					index = i;
					break;
				}
			}

			if (index < 0) {
				$scope.tags.push(blogExtra);

				let array = angular.copy($scope.article.tags);
				array.push($scope.tags[$scope.tags.length-1]);
				$scope.article.tags = angular.copy(array);
				//$scope.article.tags.push($scope.tags[$scope.tags.length-1]);
			} else {
				let j = -1;

				for (let i = $scope.article.tags.length - 1; i >= 0; i--) {
					if ($scope.article.tags[i].text == blogExtra.text) {
						j = i;
						break;
					}
				}

				if (j < 0) {
					let array = angular.copy($scope.article.tags);
					array.push($scope.tags[index]);
					$scope.article.tags = angular.copy(array);
					//$scope.article.tags.push($scope.tags[index]);
				}
			}
		});
	}

	$scope.save = function() {
		sweet.save(function() {
			let formData = {
				article: $scope.article,
			}

			if ($scope.banner != null) {
				formData.banner = $scope.banner;
			}

			rest("levelapBlog/saveArticle").multipart(formData, function() {
				sweet.success();
				sweet.close();
				$scope.cancel();
			}, function(error) {
				sweet.close();
			});
		});
	}

	function setAuthor(counter) {
		if (counter == null) {
			counter = 0;
		}

		if ($rootScope.currentUser == null || $rootScope.currentUser.fullName == null) {
			if (counter < 5) {
				setTimeout(function() {
					$scope.$apply(function() {
						counter++;
						setAuthor(counter);
					});
				}, 1000);
			} else {
				console.error("Modify 'manageArticleCtrl.js' to set authors default value to the current logged user");
			}
		} else {
			$scope.article.author = $rootScope.currentUser.fullName;
		}
	}

	AddExtraModalCtrl = function($scope, $uibModalInstance, isTag) {
		$scope.blogExtra = {};
		$scope.isTag = isTag;

		$scope.cancel = function() {
			$uibModalInstance.dismiss();
		}

		$scope.ok = function() {
			$scope.blogExtra.isTag = $scope.isTag;
			$uibModalInstance.close($scope.blogExtra);
		}
	}
});