angular.module('LevelapBlog').controller('BlogCtrl', function($scope, $rootScope, $state, importantBlogs, categories, tags, blogsPreview, openRest) {

    importantBlogs.$promise.then(function(data) {
        $scope.importantBlogs = data.content;
    });

    categories.$promise.then(function(data) {
        $scope.categories = data;
    });

    tags.$promise.then(function(data) {
        $scope.tags = data;
    });

    blogsPreview.$promise.then(function(data) {
        setPageMostSeen(data);
    });

    if ($state.current.name == 'levelapBlog.blog') {
        $state.go('levelapBlog.blog.home');
    }

    $scope.$watch('currentPageMostSeen', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            openRest("levelapBlog/findArticles").post({isMostSeen: true, page: newValue}, function(data) {
                setPageMostSeen(data);
            });
        }
    });
    function setPageMostSeen(data) {
        $scope.blogsPreview = data.content;
        $scope.totalPagesMostSeen = data.totalPages;
    }

});
