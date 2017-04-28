angular.module("Game").controller('ManageGameCtrl', function($scope, game, contentRatings, magazines, consoles, categories, sweet, rest, $state, forEach, getImageBase64, notif) {
	$scope.tabs = [{name: "General", active: true}, {name: "Multimedia", active: false}];
	$scope.game = {};

	if (game != null) {
		game.$promise.then(function(data) {
			$scope.game = data;
		});
	} else {
		magazines.$promise.then(function(data) {
			$scope.game.magazineRatings = [];

			forEach(data, function(mag) {
				$scope.game.magazineRatings.push({magazine: mag});
			});
		});
	}

	contentRatings.$promise.then(function(data) {
		$scope.contentRatings = data;
	});

	consoles.$promise.then(function(data) {
		$scope.consoles = data;

		forEach($scope.consoles, function(consl) {
			rest("archive/downloadFile").download({name: consl.logo.name, module: consl.logo.module}, function(data) {
				consl.base64 = getImageBase64(data, consl.logo.type);
			});
		});
	});

	categories.$promise.then(function(data) {
		$scope.categories = data;

		forEach($scope.categories, function(category) {
			rest("archive/downloadFile").download({name: category.blackVector.name, module: category.blackVector.module}, function(data) {
				category.base64 = getImageBase64(data, category.blackVector.type);
			});
		});
	});

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
				cover: $scope.coverImage,
				banner: $scope.bannerImage
			};

			rest("game/save").multipart(formData, function(data) {
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
});