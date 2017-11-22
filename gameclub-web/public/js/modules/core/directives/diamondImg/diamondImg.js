angular.module("Core").directive('diamondImg', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/diamondImg/diamondImg.html',
		scope: {
			ngSrc: '='
		},
		replace: true,
		link: function($scope, element, attrs) {
			$scope.getClipPath = function(id) {
				if (navigator.vendor.toLowerCase().indexOf("apple") < 0 && navigator.vendor.toLowerCase().indexOf("crios") < 0 && navigator.vendor.toLowerCase().indexOf("ipad") < 0 && navigator.vendor.toLowerCase().indexOf("iphone") < 0) {
					if (id == 'diamond') {
						return {
							'clip-path': 'url(#diamond)',
							'-webkit-clip-path': 'url(#diamond)'
						};
					}

					if (id == 'border') {
						return {
							'clip-path': 'url(#border)',
							'-webkit-clip-path': 'url(#border)'
						};
					}
				} else {
					if (id == 'diamond') {
						return {
							'clip-path': 'polygon(50% 7%, 94% 50%, 50% 94%, 6% 50%)',
							'-webkit-clip-path': 'polygon(50% 7%, 94% 50%, 50% 94%, 6% 50%)'
						};
					}
				}

				return {};
			}
		}
	};
});