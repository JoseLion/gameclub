angular.module('Core').directive('blogPreview', function($state) {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/blogPreview.html',
		scope: {
            blogId: '=',
            subtitle: '=',
            desc: '=',
            imgSrc: '=',
            date: '=',
            url: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.completeBlog = function() {
				$state.go('gameclub.blog', {idBlog: $scope.blogId});
			}

		}
	};
});
