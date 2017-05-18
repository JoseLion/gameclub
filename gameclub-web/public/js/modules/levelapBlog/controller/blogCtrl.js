angular.module('LevelapBlog').controller('BlogCtrl', function($scope, $state, $q, importantBlogs, categories, tags, blogsPreview) {

    /* TODO CAMBIAR POR PROMESAS */
    $q.all({
        importantBlogs: importantBlogs.$promise,
        categories: categories.$promise,
        tags: tags.$promise,
        blogsPreview: blogsPreview.$promise
    }).then(function(result) {
        $scope.importantBlogs = result.importantBlogs;
        $scope.categories = result.categories;
        $scope.tags = result.tags;
        $scope.blogsPreview = result.blogsPreview;
    });
    if ($state.current.name == 'levelapBlog.blog') {
        $state.go('levelapBlog.blog.home');
    }

    $scope.$watch('currentBlogPage', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            console.log('SE DEBE HACER LA CONSULTA PARA LLAMAR A MAS PREVIEWS');
        }
    });

});
