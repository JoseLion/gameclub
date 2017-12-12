angular.module('LevelapBlog').directive('articlePreview', function(openRest, $location, $state, friendlyUrl, BlogConst, urlRestPath) {
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
			$scope.$archiveUrl = urlRestPath.url + "/open/archive/download/";
			$scope.BlogConst = BlogConst;
			$scope.socialShareUrl = $location.$$absUrl;
			$scope.socialShareUrlDomain = $location.$$protocol + "://" + $location.$$host;

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
				$state.go("levelapBlog.blog.detail", {id: $scope.ngModel.id, title: friendlyUrl($scope.ngModel.title)});
			}

			$scope.getShareUrl = function() {
				if ($scope.ngModel != null) {
					return $location.$$protocol + "://" + $location.$$host + "/gameclub/blog/detail/" + $scope.ngModel.id + "/" + friendlyUrl($scope.ngModel.title);
				} else {
					return "";
				}
				
			}

			$scope.shareFacebook = function() {
				window.open("https://www.facebook.com/sharer/sharer.php?u=" + $location.$$protocol + "://" + $location.$$host + "/gameclub/blog/detail/" + $scope.ngModel.id + "/" + friendlyUrl($scope.ngModel.title), "Compartelo en Facebbok", "width=500,height=500");
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