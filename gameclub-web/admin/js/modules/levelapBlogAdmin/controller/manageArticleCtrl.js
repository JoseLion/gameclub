angular.module("LevelapBlogAdmin").controller('ManageArticleCtrl', function($scope, $rootScope, article, categories, tags, $state, $uibModal, sweet, rest, getImageBase64) {
	$scope.article = {
		squareCrop: {
			a: 0,
			b: 0,
			c: 1.0
		},
		diamondCrop: {
			a: 0,
			b: 0,
			c: 1.0
		}
	};

	setAuthor();
	let isDragging = false;
	let dx = 0;
	let dy = 0;

	$scope.tinymceOptions = {
		onChange: function(e) {},
		inline: false,
		plugins: 'advlist autolink link image lists charmap print preview',
		skin: 'lightgray',
		theme: 'modern',
		height: 330,
		language: 'es_MX'
	};

	$scope.zoomOptions = {
		ceil: 2.0,
		floor: 0.1,
		step: 0.01,
		precision: 2,
		vertical: true,
		showSelectionBar: true
	};

	$scope.getCropStyle = function() {
		let diamondDiv = angular.element("#diamond-div");
		return {
			height: diamondDiv[0].clientWidth + 'px'
		};
	}

	$scope.beginDrag = function($event) {
		isDragging = true;
		dx = $event.offsetX;
		dy = $event.offsetY;
	}

	$scope.endDrag = function() {
		isDragging = false;
	}

	$scope.drag = function($event, shape) {
		if (isDragging) {
			if (shape === 'square') {
				$scope.article.squareCrop.a += ($event.offsetX - dx) / $scope.article.diamondCrop.c;
				$scope.article.squareCrop.b += ($event.offsetY - dy) / $scope.article.diamondCrop.c;
			}

			if (shape === 'diamond') {
				$scope.article.diamondCrop.a += ($event.offsetX - dx) / $scope.article.diamondCrop.c;
				$scope.article.diamondCrop.b += ($event.offsetY - dy) / $scope.article.diamondCrop.c;
			}
			
			dx = $event.offsetX;
			dy = $event.offsetY;
		}
	}

	$scope.getPositionStyle = function(obj) {
		return {
			left: obj.a + 'px',
			top: obj.b + 'px',
			zoom: (obj.c * 100) + '%'
		};
	}

	if (article != null) {
		article.$promise.then(function(data) {
			$scope.article = data;

			if ($scope.article.squareCrop == null) {
				$scope.article.squareCrop = {
					a: 0,
					b: 0,
					c: 1.0
				};
			}

			if ($scope.article.diamondCrop == null) {
				$scope.article.diamondCrop = {
					a: 0,
					b: 0,
					c: 1.0
				};
			}

			setTimeout(function() {
				$scope.$broadcast('rzSliderForceRender');
			}, 0);
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
		} else {
			$scope.bannerBase64 = '//:0';
		}

		setTimeout(function() {
			$scope.$broadcast('rzSliderForceRender');
		}, 0);
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