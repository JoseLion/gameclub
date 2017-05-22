angular.module('LevelapBlog').controller('BlogCtrl', function($scope, $rootScope, $state, importantBlogs, categories, tags, blogsPreview) {

    importantBlogs.$promise.then(function(data) {
        $scope.importantBlogs = data;
    });

    categories.$promise.then(function(data) {
        $scope.categories = data;
    });

    tags.$promise.then(function(data) {
        $scope.tags = data;
    });

    blogsPreview.$promise.then(function(data) {
        $scope.blogsPreview = data;
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
