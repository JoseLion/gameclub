angular.module("Game").controller('ManageGameCtrl', function($scope, game, contentRatings, sweet, rest, $state) {
	$scope.tabs = [{name: "General", active: true}, {name: "Multimedia", active: false}];
	$scope.game = {};

	if (game != null) {
		game.$promise.then(function(data) {
			$scope.game = data;
		});
	}

	contentRatings.$promise.then(function(data) {
		$scope.contentRatings = data;
	});
});