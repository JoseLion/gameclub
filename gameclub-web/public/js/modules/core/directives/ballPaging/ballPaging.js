angular.module('Core').directive('ballPaging', function($ocLazyLoad) {
	$ocLazyLoad.load('js/modules/core/directives/ballPaging/ballPaging.less');

	return {
		restrict: 'E',
		require: 'ngModel',
		templateUrl: 'js/modules/core/directives/ballPaging/ballPaging.html',
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
					$scope.pageList = [];

					for (let i = 0; i < newValue; i++) {
						$scope.blogPage.push({
							number: i,
							active: ($scope.ngModel == i ? true : false)
						});

						$scope.pageList.push({
							number: i,
							active: ($scope.ngModel == i ? true : false)
						});
					}
				}
			});
		}
	};
});
