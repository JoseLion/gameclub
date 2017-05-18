angular.module('LevelapBlog').controller('BlogHomeCtrl', function($scope, blogs) {

    blogs.$promise.then(function(data) {
        $scope.blogs = data;
    });

});
