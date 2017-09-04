angular.module("Core").directive('score', function() {
	return {
		restrict: 'E',
		require: 'ngModel',
		templateUrl: 'js/modules/core/directives/score.html',
		cssUrl: 'js/modules/core/directives/score.css1',
		scope: {
			ngModel: '='
		},
		link: function($scope, element, attrs, ctrl) {
			$scope.stars = [1, 2, 3, 4, 5];

			$scope.setScore = function(star) {
				if (!$scope.isDisabled) {
					$scope.ngModel = star;
				}
			}

			$scope.mouseEnter = function(star) {
				if (!$scope.isDisabled) {
					for (let i = 1; i <= star; i++) {
						angular.element("#score-star-" + i).addClass("hover");
					}
				}
			}

			$scope.mouseLeave = function(star) {
				if (!$scope.isDisabled) {
					for (let i = 1; i <= star; i++) {
						angular.element("#score-star-" + i).removeClass("hover");
					}
				}
			}

			$scope.$watch(function() {return attrs.disabled;}, function(newValue, oldValue) {
				if (newValue == true || newValue === "") {
					$scope.isDisabled = true;
				} else {
					$scope.isDisabled = false;
				}
			});
		}
	};
});