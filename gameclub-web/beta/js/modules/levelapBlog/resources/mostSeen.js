angular.module('LevelapBlog').directive('mostSeen', function($state, friendlyUrl, BlogConst) {
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
        templateUrl: baseSrc.concat('resources/mostSeen.html'),
        required: 'ngModel',
        scope: {
            ngModel: '=',
            pages: '=',
            activePage: '=',
			title: '@',
			size: '='
        },
        replace: true,
        link: function($scope, element, attrs, ctrl) {
            $scope.BlogConst = BlogConst;
            
            if ($scope.activePage == null) {
                $scope.activePage = 0;
            }

			if ($scope.size == null) {
				$scope.filterSize = 200;
			} else {
				$scope.filterSize = $scope.size;
			}

            $scope.changePage = function(page) {
                $scope.pageList[$scope.activePage].active = false;
                page.active = true;
                $scope.activePage = page.number;
            }

            $scope.$watch("pages", function(newValue, oldValue) {
                if (newValue != null) {
                    $scope.pageList = [];
                    for (let i = 0; i < newValue; i++) {
                        $scope.pageList.push({
                            number: i,
                            active: ($scope.activePage == i ? true : false)
                        });
                    }
                }
            });

            $scope.goToArticle = function(preview) {
                $state.go("levelapBlog.blog.detail", {id: preview.id, title: friendlyUrl(preview.title)});
            }
        }
    };
});
