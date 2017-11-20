angular.module('Core').directive('blogPaging', function() {
	return {
		restrict: 'E',
		require: 'ngModel',
		templateUrl: 'js/modules/core/directives/blogPaging.html',
		scope: {
			pages: '=',
			ngModel: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			if ($scope.ngModel == null) {
				ctrl.$setViewValue(0);
			}

			$scope.changePage = function(page) {
				$scope.pageList[$scope.ngModel].active = false;
				page.active = true;
				ctrl.$setViewValue(page.number);
			}

			$scope.$watch("pages", function(newValue, oldValue) {
				if (newValue != null) {
					$scope.blogPage = [];
					for(let i = 0; i < newValue; i++) {
						$scope.blogPage.push({
							number: i,
							active: ($scope.ngModel == i ? true : false)
						});
					}
				}
			});
		}
	};
});
