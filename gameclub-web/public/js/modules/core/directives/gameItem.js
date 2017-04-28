angular.module('Core').directive('gameItem', function($state) {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/gameItem.html',
		required: 'ngModel',
		scope: {
            ngModel: '=',
            src: '=',
            title: '=',
            coins: '=',
            rating: '=',
            types: '=',
            contentRating: '=',
            showCoins: '=',
			platform: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
            for(let i=0 ; i<=$scope.rating ; i++) {
                angular.element('.score-'+i).attr('src', 'img/star-score.svg')
            }

			$scope.viewDetails = function() {
				$state.go('gameclub.game', {idGame: $scope.ngModel.id});
			}
		}
	};
});
