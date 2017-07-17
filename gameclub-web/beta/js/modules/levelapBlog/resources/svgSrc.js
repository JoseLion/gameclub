angular.module("LevelapBlog").directive('svgSrc', function() {
	return {
		restrict: 'A',
		scope: {
			svgSrc: '='
		},
		link: function($scope, element, attrs) {
			setTimeout(function() {
				$scope.$apply(function() {
					if ($scope.svgSrc != null) {
						attrs.$set('xlink:href', $scope.svgSrc);
					} else {
						attrs.$set('xlink:href', '');
					}
				});
			}, 0);
		}
	};
});