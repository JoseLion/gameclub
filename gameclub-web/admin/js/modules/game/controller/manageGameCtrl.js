angular.module("Game").controller('ManageGameCtrl', function($scope, game, contentRatings, magazines, consoles, categories, sweet, rest, $state, forEach, getImageBase64, notif, $sce, $q) {
	$scope.tabs = [{name: "General", active: true}, {name: "Multimedia", active: false}];
	$scope.game = {
		diamondCrop: {
			a: 0,
			b: 0,
			c: 1.0
		}
	};
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

			if ($scope.game.diamondCrop == null) {
				$scope.game.diamondCrop = {
					a: 0,
					b: 0,
					c: 1.0
				};
			}

			setTimeout(function() {
				forEach($scope.game.magazineRatings, function(gameMagazine, i) {
					let element = angular.element("#rating-slider-" + i)[0];
					element.updateData({
						from: gameMagazine.rating
					});
				});
			}, 100);
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

	/* ----------------- CROP ------------------ */

	let isDragging = false;
	let dx = 0;
	let dy = 0;

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
			$scope.game.diamondCrop.a += $event.offsetX - dx;
			$scope.game.diamondCrop.b += $event.offsetY - dy;
			
			dx = $event.offsetX;
			dy = $event.offsetY;
		}
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
});