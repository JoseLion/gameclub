angular.module('LevelapBlog').directive('blogNavigation', function() {
	let baseSrc;
    for (let i = document.getElementsByTagName("script").length - 1; i >= 0; i--) {
        let script = angular.element(document.getElementsByTagName("script")[i]);
        if (script.attr("src") != null && script.attr("src").indexOf("levelapBlog.js") > -1) {
            baseSrc = script.attr("src").substring(0, script.attr("src").indexOf("levelapBlog.js"));
            break;
        }
    }
	return {
		restrict: 'E',
		templateUrl: baseSrc.concat('resources/blogNavigation.html'),
		scope: {
			pages: '=',
			activePage: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			let beforeIdx;
			let afterIdx;

			if ($scope.activePage == null) { $scope.activePage = 0; }
			$scope.previousPage = function() {
				if($scope.activePage > 0) {
					$scope.pageList[$scope.activePage--].active = false;
					$scope.pageList[$scope.activePage].active = true;
					updatePageVisible($scope.pageList[$scope.activePage]);
				}
			};
			$scope.nextPage = function() {
				if($scope.activePage < $scope.pages - 1) {
					$scope.pageList[$scope.activePage++].active = false;
					$scope.pageList[$scope.activePage].active = true;
					updatePageVisible($scope.pageList[$scope.activePage]);
				}
			};
			$scope.changePage = function(page) {
				$scope.pageList[$scope.activePage].active = false;
				page.active = true;
				$scope.activePage = page.number;
				updatePageVisible(page);
			};
			function updatePageVisible(page) {
				if(beforeIdx != null && afterIdx != null) {
					$scope.pageList[beforeIdx].visible = false;
					$scope.pageList[afterIdx].visible = false;
				}
				if($scope.activePage != 0 && $scope.activePage < ($scope.pages - 2)) {
					beforeIdx = $scope.activePage - 1;
					afterIdx = $scope.activePage + 1;

					$scope.pageList[beforeIdx].visible = true;
					page.visible = true;
					$scope.pageList[afterIdx].visible = true;
				} else {
					beforeIdx = null;
					afterIdx = null;
					page.visible = true;
					if($scope.activePage == 0) {
						for(let i = 0 ; i < $scope.pages ; i++) {
							$scope.pageList[i].visible = (i < 3 ? true : false);
						}
					} else {
						for(let i = 0 ; i < $scope.pages ; i++) {
							$scope.pageList[i].visible = (i > $scope.pages - 4 ? true : false);
						}
					}
				}
			}
			$scope.$watch("pages", function(newValue, oldValue) {
				if (newValue != null) {
					$scope.pageList = [];
					for(let i = 0; i < newValue; i++) {
						$scope.pageList.push({
							number: i,
							active: ($scope.activePage == i ? true : false),
							visible: (i < 3 || i == newValue - 1) ? true : false
						});
					}
				}
			});

		}
	};
});
