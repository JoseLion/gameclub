angular.module('Core').directive('mostPlayed', function() {
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
			console.log('FUNCIONALIDAD DE INGRESAR EL OBJECTO DEL JUEGO QUE CONTIENE LA IMAGEN (PATH) Y EL RANKING');
            $scope.viewGameDetail = function(game) {
                console.log('SE DEBE HACER LA VISUALIZACIÓN DEL JUEGO');
            };

		}
	};
});
