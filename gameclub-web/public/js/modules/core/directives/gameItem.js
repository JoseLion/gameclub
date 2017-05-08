angular.module('Core').directive('gameItem', function($state, friendlyUrl) {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/gameItem.html',
		required: 'ngModel',
		scope: {
            ngModel: '=',
            showCoins: '=?'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
            for (let i = 0; i <= $scope.ngModel.rating; i++) {
                angular.element('.score-'+i).attr('src', 'img/star-score.svg')
            }

			$scope.viewDetails = function() {
				$state.go('gameclub.game', {id: $scope.ngModel.id, name: friendlyUrl($scope.ngModel.name)});
			}
		}
	};
});
