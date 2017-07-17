angular.module("LevelapBlog").directive('svgSrc', function() {
	return {
		restrict: 'A',
		scope: {
			svgSrc: '='
		},
		link: function($scope, element, attrs) {
			$scope.$watch('svgSrc', function(newValue, oldValue) {
				setTimeout(function() {
					$scope.$apply(function() {
						if (newValue != null) {
							attrs.$set('xlink:href', newValue);
						} else {
							attrs.$set('xlink:href', '');
						}
					});
				}, 0);
			});
		}
	};
});