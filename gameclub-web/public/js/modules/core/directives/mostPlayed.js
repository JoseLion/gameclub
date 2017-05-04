angular.module('Core').directive('mostPlayed', function($state) {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/mostPlayed.html',
		scope: {
			games: '=',
            previous: '&',
            next: '&'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {

			$scope.viewGameDetail = function(game) {
				$state.go('gameclub.game', {idGame: 0});
            };

		}
	};
});
