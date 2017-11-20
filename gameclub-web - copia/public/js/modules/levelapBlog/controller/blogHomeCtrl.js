/*!
 * blogHomeCtrl.js - v0.1
 * http://www.levelapsoftware.com
 * License: MIT
 * Requirements:
 * - RESTful Web Services
 * - openRest factory
 */
angular.module('LevelapBlog').controller('BlogHomeCtrl', function($scope, blogs, openRest) {
    blogs.$promise.then(function(data) {
        setPageHome(data);
    });
    $scope.currentPageHome = 0;
    $scope.$watch('currentPageHome', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            openRest("levelapBlog/findArticles").post({isHomePage: true, page: newValue}, function(data) {
                setPageHome(data);
            });
        }
    });
    function setPageHome(data) {
        $scope.blogs = data.content;
        $scope.totalPagesHome = data.totalPages;
    }
});
