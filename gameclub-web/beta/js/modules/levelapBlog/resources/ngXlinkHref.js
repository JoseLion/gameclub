angular.module("LevelapBlog").directive('ngXlinkHref', function() {
	return {
		restrict: 'A',
		scope: {
			ngXlinkHref: '='
		},
		link: function($scope, element, attrs) {
			$scope.$watch('ngXlinkHref', function(newValue, oldValue) {
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