angular.module('WorkForUs').controller('WorkForUsCtrl', function($scope, $rootScope, $location, anchor, $state, friendlyUrl, sweet, openRest, notif, forEach, friendlyUrl, blogsPreview, notif, Const) {
    if (anchor != null) {
        $location.hash(anchor);
        //$anchorScroll.yOffset = angular.element('#fixedbar')[0].offsetHeight;
    }

    $scope.currentBlogPage = 0;
    blogsPreview.$promise.then(function(data) {
        setPageBlogsMostSeen(data);
    });
    $scope.$watch('currentPageMostSeen', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            openRest("levelapBlog/findArticles").post({isMostSeen: true, page: newValue}, function(data) {
                setPageBlogsMostSeen(data);
            });
        }
    });
    function setPageBlogsMostSeen(data) {
        $scope.blogsPreview = data.content;
        $scope.blogsPreview.forEach(function(preview) {
            if(preview.squareCrop != null) {
                preview.crop = {
                    transform: 'translate(' + preview.squareCrop.a + 'px,' + preview.squareCrop.b + 'px)',
                    zoom: (preview.squareCrop.c * 0.75)
                };
            }
        });
        $scope.totalPagesMostSeen = data.totalPages;
    }

});
