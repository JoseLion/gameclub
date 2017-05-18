angular.module('LevelapBlog').controller('BlogDetailCtrl', function($scope, article, comments) {

    article.$promise.then(function(data) {
        $scope.article = data;
        $scope.showInfo = true;
    });

    comments.$promise.then(function(data) {
        $scope.comments = data;
    });

});
