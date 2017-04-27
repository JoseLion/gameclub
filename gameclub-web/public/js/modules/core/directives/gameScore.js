angular.module('Core').directive('gameScore', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/gameScore.html',
		scope: {
			src: '=',
			score: '=',
			bgColor: '@'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			if(attrs.noScore == '' || attrs.noScore) {
				$scope.hideScore = true;
			} else {
				$scope.hideScore = false;
				if($scope.score != null && !isNaN($scope.score)) {
					for(let i=0 ; i<=$scope.score ; i++) {
						angular.element('.score-'+i).attr('src', 'img/star-score.svg')
					}
				}
			}
		}
	};
});
