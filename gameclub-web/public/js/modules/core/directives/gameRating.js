angular.module('Core').directive('gameRating', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/gameRating.html',
		scope: {
			src: '=?',
			rating: '=',
			bgColor: '@',
			ngClick: '&',
			noSelection: '=',
			imageArchive: '=?',
			crop: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.hideRating = false;
			if(attrs.noRating == '' || attrs.noRating) {
				$scope.hideRating = true;
			}
			$scope.$watch('crop', function(newValue, oldValue) {
				if(newValue != null) {
					$scope.finalCrop = {
	                    transform: 'translate(' + newValue.a + 'px,' + newValue.b + 'px)',
						zoom: (newValue.c / 2)
	                };
				}
			});
			if($scope.noSelection == null || $scope.noSelection == '' || !$scope.noSelection) {
				element.find('.background').after().mouseover(function(e) {
					element.find('.background').removeClass('no-hover');
					element.find('.background').addClass('hover');
					element.find('.background').addClass('pointer');
				});

				element.find('.background').after().mouseout(function(e) {
					element.find('.background').removeClass('hover');
					element.find('.background').addClass('no-hover');
					element.find('.background').removeClass('pointer');
				});
			}

		}
	};
});
