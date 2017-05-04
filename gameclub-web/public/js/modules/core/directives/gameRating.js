angular.module('Core').directive('gameRating', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/gameRating.html',
		scope: {
			src: '=',
			score: '=',
			bgColor: '@',
			ngClick: '&'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			if(attrs.noScore == '' || attrs.noScore) {
				$scope.hideScore = true;
			} else {
				$scope.hideScore = false;
				if($scope.score != null && !isNaN($scope.score)) {
					for(let i=1 ; i<=$scope.score ; i++) {
						angular.element(element[0].querySelector('.rating-'+i)).addClass('yes');
					}
				}
			}
		}
	};
});
