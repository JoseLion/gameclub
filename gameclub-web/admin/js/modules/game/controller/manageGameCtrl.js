angular.module("Game").controller('ManageGameCtrl', function($scope, game, contentRatings, magazines, sweet, rest, $state, forEach) {
	$scope.tabs = [{name: "General", active: true}, {name: "Multimedia", active: false}];
	$scope.game = {};

	if (game != null) {
		game.$promise.then(function(data) {
			$scope.game = data;
		});
	} else {
		magazines.$promise.then(function(data) {
			console.log("data: ", data);
			$scope.game.magazineRatings = [];

			forEach(data, function(mag) {
				$scope.game.magazineRatings.push({magazine: mag});
			});
		});
	}

	contentRatings.$promise.then(function(data) {
		$scope.contentRatings = data;
	});
});