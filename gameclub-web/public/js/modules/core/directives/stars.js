angular.module("Core").directive('stars', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/stars.html',
		require: 'ngModel',
		scope: {

		},
		link: function($scope, element, attrs, ctrl) {
			$scope.stars = [{}, {}, {}, {}, {}];
		}
	};
});