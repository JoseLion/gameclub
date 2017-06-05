angular.module('Home').controller('HomeCtrl', function($scope, $rootScope, $location, anchor, $state, friendlyUrl, sweet, openRest, notif, forEach, friendlyUrl, blogsPreview) {

    if (anchor != null) {
        $location.hash(anchor);
        //$anchorScroll.yOffset = angular.element('#fixedbar')[0].offsetHeight;
    }

    $scope.contactUs = {};
    $scope.sendContactUs = function() {
        sweet.default("Nos enviará un correo con su mensaje e información", function() {
            openRest("publicUser/sendContactUs").post($scope.contactUs, function() {
                notif.success("El correo se envió con éxito");
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
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
            preview.crop = {
                transform: 'translate(' + preview.squareCrop.a + 'px,' + preview.squareCrop.b + 'px) scale(' + preview.squareCrop.c + ')'
            };
        });
        $scope.totalPagesMostSeen = data.totalPages;
    }

});
