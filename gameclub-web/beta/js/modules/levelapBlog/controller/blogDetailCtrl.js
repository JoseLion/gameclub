angular.module('LevelapBlog').controller('BlogDetailCtrl', function($scope, article, comments, $state, $location, friendlyUrl) {
    article.$promise.then(function(data) {
        $scope.article = data;
        $scope.showInfo = true;
        $state.current.data.description = $scope.article.summary;

        $state.current.data.ogUrl = $location.$$absUrl.split("#")[0] + "#!/gameclub/blog/detail/" + $scope.article.id + "/" + friendlyUrl($scope.article.title);
        $state.current.data.ogTitle = $scope.article.title;
        $state.current.data.ogDescription = $scope.article.summary;
        //$state.current.data.ogImage = 
    });

    comments.$promise.then(function(data) {
        $scope.comments = data;
    });

    $scope.prevArticle = function(article) {
        if (article.prevArticleId != null) {
            $state.go('levelapBlog.blog.detail', {id: article.prevArticleId, title: article.prevArticleTitle});
        }
    };

    $scope.nextArticle = function(article) {
        if (article.nextArticleId != null) {
            $state.go('levelapBlog.blog.detail', {id: article.nextArticleId, title: article.nextArticleTitle});
        }
    };
});
