angular.module('Core').directive('blogPaging', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/blogPaging.html',
		scope: {
			pages: '=',
			current: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			if($scope.current == null) {
				$scope.current = 0;
			}
			$scope.pageList = [];
			for(let i=0 ; i<$scope.pages ; i++) {
				$scope.pageList.push({active: i==0 ? true : false});
			}

			$scope.changePage = function(page, idx) {
				$scope.pageList[$scope.current].active = false;
				page.active = true;
				$scope.current = idx;
			};

		}
	};
});
