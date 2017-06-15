angular.module('LevelapBlog').directive('articlePreview', function(openRest, $location, $state) {
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
		templateUrl: baseSrc.concat('resources/articlePreview.html'),
		required: 'ngModel',
		scope: {
			ngModel: '=',
			isComplete: '@',
			comments: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.socialShareUrl = $location.$$absUrl;
			$scope.socialShareUrlDomain = $location.$$protocol + "://" + $location.$$host;

			if ($state.current.name == 'levelapBlog.blog.home') {
				$state.isBlogHome = true;
			} else {
				$state.isBlogHome = false;
			}

			$scope.showAllInfo = false;
			$scope.currentPage = 0;
			$scope.showCommentsSection = $scope.comments == null ? false : $scope.comments.content.length > 0;
			
			$scope.$on('check-comments', function() {
				openRest("levelapBlog/getCommentsOf/:articleId/:page").get({articleId: $scope.ngModel.id, page: 0}, function(data) {
                    $scope.comments = data;
                    $scope.showCommentsSection = $scope.comments.content.length > 0;
                });
			});

			$scope.goToDetails = function() {
				$state.go("levelapBlog.blog.detail", {id: $scope.ngModel.id, title: $scope.ngModel.title});
			}

			if ($scope.isComplete != null) {
				if($scope.isComplete == '') {
					$scope.showAllInfo = true
				} else {
					$scope.showAllInfo = JSON.parse($scope.isComplete);
				}
			}
		}
	};
});
