angular.module('Core').directive('loading', function() {
	return {
		restrict: 'E',
		template: '<div id="loading" ng-if="showing" ng-style="$parent.style"></div>',
        required: 'showing',
		scope: {
			showing: '=',
			top: '@',
			bottom: '@'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.style = {};
			if($scope.top != null && $scope.top != '') {
				$scope.style.top = $scope.top;
			}
			if($scope.bottom != null && $scope.bottom != '') {
				$scope.style.bottom = $scope.bottom;
			}
		}
	};
});
