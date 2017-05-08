angular.module('Core').directive('gameRating', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/gameRating.html',
		scope: {
			src: '=',
			rating: '=',
			bgColor: '@',
			ngClick: '&',
			noSelection: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.hideRating = false;
			if(attrs.noRating == '' || attrs.noRating) {
				$scope.hideRating = true;
			}

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
