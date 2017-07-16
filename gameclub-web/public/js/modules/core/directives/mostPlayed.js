angular.module('Core').directive('mostPlayed', function($state, $rootScope, friendlyUrl) {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/mostPlayed.html',
		scope: {
			games: '=',
			previous: '&',
			next: '&',
			title: '@'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {

			$scope.consoles = $rootScope.consoles;
			$scope.$watch('consoles', function(newValue, oldValue) {
				if(newValue != null) {
					$scope.gameConsole = {
						selected : $scope.consoles[0]
					};
				}
			});

			$scope.viewGameDetail = function(game) {
				$state.go('gameclub.game', {id: game.id, consoleId: $scope.gameConsole.selected.id, name: friendlyUrl(game.name)});
			};

		}
	};
});
