angular.module('Core').directive('mostPlayed', function($state) {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/mostPlayed.html',
		scope: {
			games: '=',
            previous: '&',
            next: '&',
			gameConsoles: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {

			$scope.$watch('gameConsoles', function(newValue, oldValue) {
				if(newValue != null) {
					$scope.gameConsole = {
						selected : $scope.gameConsoles[0]
					};
				}
			});

			$scope.viewGameDetail = function(game) {
				$state.go('gameclub.game', {idGame: 0});
            };

		}
	};
});
