angular.module('Core').directive('gameItem', function($state, friendlyUrl, $ocLazyLoad, urlRestPath) {
	$ocLazyLoad.load(['js/modules/core/directives/gameItem/gameItem.less', 'js/modules/core/directives/gameItem/gameItem.responsive.less']);

	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/gameItem/gameItem.html',
		required: 'ngModel',
		scope: {
            ngModel: '=',
            console: '=?',
            showCoins: '=?',
            coins: '=?',
            inverse: '=?',
            isEditable: '=?',
            isBorrowed: '=?',
            onEdit: '&?'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.$archiveUrl = urlRestPath.url + "/open/archive/download/";
			
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