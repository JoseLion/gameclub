angular.module("Core").directive('gameScore', function() {
	return {
		restrict: 'E',
		templateUrl: "js/modules/core/directives/gameScore.html",
		scope: {
			src: '=',
			score: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			if($scope.score != null && !isNaN($scope.score)) {
				for(let i=0 ; i<=$scope.score ; i++) {
					angular.element('.score-'+i).attr('src', 'img/star-score.png')
				}
			}
		}
	};
});
