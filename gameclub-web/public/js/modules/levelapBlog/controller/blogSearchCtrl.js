angular.module('LevelapBlog').controller('BlogSearchCtrl', function($scope, articles, searchValue, openRest) {

    articles.$promise.then(function(data) {
        setPageSearch(data);
        $scope.showInfo = true;
    });

    $scope.$watch('currentPageSearch', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            openRest("levelapBlog/findArticles").post({isSearch: true, text: searchValue, page: newValue}, function(data) {
                setPageSearch(data);
            });
        }
    });
    function setPageSearch(data) {
        $scope.articles = data.content;
        $scope.totalPagesSearch = data.totalPages;
    }

});
