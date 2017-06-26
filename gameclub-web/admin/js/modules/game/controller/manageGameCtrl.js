angular.module("Game").controller('ManageGameCtrl', function($scope, game, contentRatings, magazines, consoles, categories, sweet, rest, $state, forEach, getImageBase64, notif, $sce, $q, cropImage) {
	$scope.tabs = [{name: "General", active: true}, {name: "Multimedia", active: false}];
	$scope.game = {};
	$scope.images = {};

	$q.all({
		game: game != null ? game.$promise : null,
		magazines: magazines.$promise,
		contentRatings: contentRatings.$promise,
		consoles: consoles.$promise,
		categories: categories.$promise
	}).then(function(result) {
		$scope.contentRatings = result.contentRatings;

		$scope.consoles = result.consoles;

		forEach($scope.consoles, function(consl) {
			rest("archive/downloadFile").download({name: consl.blackLogo.name, module: consl.blackLogo.module}, function(data) {
				consl.base64 = getImageBase64(data, consl.blackLogo.type);
			});
		});

		$scope.categories = result.categories;

		forEach($scope.categories, function(category) {
			rest("archive/downloadFile").download({name: category.blackVector.name, module: category.blackVector.module}, function(data) {
				category.base64 = getImageBase64(data, category.blackVector.type);
			});
		});

		if (result.game != null) {
			$scope.game = result.game;

			setTimeout(function() {
				forEach($scope.game.magazineRatings, function(gameMagazine, i) {
					let element = angular.element("#rating-slider-" + i)[0];
					element.updateData({
						from: gameMagazine.rating
					});
				});
			}, 100);

			setTimeout(function() {
				$scope.$broadcast('rzSliderForceRender');
			}, 0);
		} else {
			$scope.game.magazineRatings = [];

			forEach(result.magazines, function(mag) {
				$scope.game.magazineRatings.push({magazine: mag});
			});
		}
	});

	$scope.getSliderOptions = function(gameMagazine) {
		return {
			min: 0,
			max: 100,
			type: 'single',
			step: 1,
			//postfix: '%',
			prettify: false,
			hasGrid: true,
			onChange: function(val) {
				gameMagazine.rating = val.fromNumber;
			}
		};
	}

	$scope.changeVideo = function() {
		if ($scope.game.trailerUrl != null) {
			if ($scope.game.trailerUrl.indexOf('youtube.com') == -1) {
				notif.warning("El URL ingresado no es de un video de YouTube");
			} else {
				$scope.url = $sce.trustAsResourceUrl($scope.game.trailerUrl);
			}
		}
	}

	$scope.getVideoHeight = function() {
		let frame = angular.element("#video-frame");
		let height = frame.width() * 9.0 / 16;
		return height;
	}

	$scope.save = function() {
		sweet.save(function() {
			let formData = {
				game: $scope.game,
				cover: $scope.images.cover,
				banner: $scope.images.banner
			};

			if ($scope.images.diamond != null) {
				formData.diamond = base64ToFile($scope.images.diamond, "diamond - " + $scope.images.cover.name);
			}

			rest("game/save").multipart(formData, function() {
				sweet.success();
				sweet.close();
				$scope.cancel();
			}, function(error) {
				sweet.error(error.data.message);
			});
		});
	}

	$scope.cancel = function() {
		$state.go("^.viewGames");
	}

	$scope.cropImage = function(shape) {
		cropImage($scope.coverBase64, shape).result.then(function(croppedImage) {
			$scope.images.diamond = croppedImage;
		});
	}

	$scope.$watch("images.cover", function(newValue, oldValue) {
		if (newValue != null) {
			let reader = new FileReader();
			reader.onload = function() {
				$scope.coverBase64 = getImageBase64(reader.result, newValue.type);
			};

			reader.readAsArrayBuffer(newValue);
		} else {
			$scope.coverBase64 = '//:0';
		}
	});

	$scope.$watch("coverBase64", function(newValue, oldValue) {
		if (newValue == null || newValue == '//:0') {
			$scope.images.diamond = '//:0';
		}
	});

	function base64ToFile(base64, filename) {
		let arr = base64.split(','), mime = arr[0].match(/:(.*?);/)[1]
		let bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);

		while(n--){
			u8arr[n] = bstr.charCodeAt(n);
		}

		return new File([u8arr], filename, {type:mime});
	}
});