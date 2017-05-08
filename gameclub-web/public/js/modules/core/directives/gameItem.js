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
			$scope.viewDetails = function() {
				$state.go('gameclub.game', {id: $scope.ngModel.id, name: friendlyUrl($scope.ngModel.name)});
			}
		}
	};
});
