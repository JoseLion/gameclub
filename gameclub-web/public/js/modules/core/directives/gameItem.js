angular.module('Core').directive('gameItem', function($state, friendlyUrl) {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/gameItem.html',
		required: 'ngModel',
		scope: {
            ngModel: '=',
            console: '=?',
            showCoins: '=?',
            inverse: '=?',
            isEditable: '=?',
            onEdit: '&?'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.viewDetails = function() {
				if (!$scope.isEditable) {
					$state.go('gameclub.game', {id: $scope.ngModel.id, consoleId: $scope.console.id, name: friendlyUrl($scope.ngModel.name)});
				} else {
					if ($scope.onEdit != null) {
						$scope.onEdit();
					}
				}
			}
		}
	};
});