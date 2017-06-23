angular.module("LevelapBlogAdmin").controller('ManageArticleCtrl', function($scope, $rootScope, article, categories, tags, $state, $uibModal, sweet, rest, getImageBase64, notif) {
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
		} else {
			$scope.bannerBase64 = '//:0';
			$scope.diamondCropImage = '//:0';
			$scope.squareCropImage = '//:0';
		}
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
		if ($scope.diamondCropImage == null || $scope.diamondCropImage == '//:0' || $scope.squareCropImage == null || $scope.squareCropImage == '//:0') {
			notif.danger("Se debe hacer los dos recortes para continuar")
		} else {
			sweet.save(function() {
				let formData = {
					article: $scope.article,
				}

				if ($scope.banner != null) {
					formData.banner = $scope.banner;
				}

				if ($scope.diamondCropImage != null) {
					formData.diamond = base64ToFile($scope.diamondCropImage, "diamond - " + $scope.banner.name);
				}

				if ($scope.squareCropImage != null) {
					formData.square = base64ToFile($scope.squareCropImage, "square - " + $scope.banner.name);
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
	}

	$scope.cropImage = function(shape) {
		let modal = $uibModal.open({
			size: 'md',
			backdrop: 'static',
			templateUrl: 'cropImage.html',
			controller: function($scope, $uibModalInstance, base64Image, shape) {
				$scope.crop = {};
				$scope.crop.base64Image = base64Image;
				$scope.shape = shape;

				$scope.ok = function() {
					$uibModalInstance.close($scope.crop.croppedImage);
				}

				$scope.cancel = function() {
					$uibModalInstance.dismiss();
				}
			},
			resolve: {
				loadPlugin: function($ocLazyLoad) {
					let baseSrc;

					for (let i = document.getElementsByTagName("script").length - 1; i >= 0; i--) {
						let script = angular.element(document.getElementsByTagName("script")[i]);

						if (script.attr("src") != null && script.attr("src").indexOf("levelapBlogAdmin.js") > -1) {
							baseSrc = script.attr("src").substring(0, script.attr("src").indexOf("levelapBlogAdmin.js"));
							break;
						}
					}

					return $ocLazyLoad.load([{
						name: 'angular-img-cropper',
						files: [baseSrc + 'plugins/angular-img-cropper/angular-img-cropper.js']
					}]);
				},

				base64Image: function() {
					return $scope.bannerBase64;
				},

				shape: function() {
					return shape;
				}
			}
		});

		modal.result.then(function(croppedImage) {
			if (shape == 'diamond') {
				$scope.diamondCropImage = croppedImage;
			} else {
				$scope.squareCropImage = croppedImage;
			}
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

	function base64ToFile(base64, filename) {
		let arr = base64.split(','), mime = arr[0].match(/:(.*?);/)[1]
		let bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);

		while(n--){
			u8arr[n] = bstr.charCodeAt(n);
		}

		return new File([u8arr], filename, {type:mime});
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