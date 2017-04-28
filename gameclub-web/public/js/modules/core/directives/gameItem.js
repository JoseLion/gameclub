angular.module('Core').directive('gameItem', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/gameItem.html',
		scope: {
            src: '=',
            title: '=',
            coins: '=',
            rating: '=',
            types: '=',
            contentRating: '=',
            showCoins: '=',
			platform: '=',
            ngClick: '&'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			console.log($scope)

            for(let i=0 ; i<=$scope.rating ; i++) {
                angular.element('.score-'+i).attr('src', 'img/star-score.svg')
            }
		}
	};
});
