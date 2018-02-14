angular.module('WorkForUs').controller('WorkForUsCtrl', function($scope, blogsPreview, anchor, $location, sweet, openRest, notif, forEach) {
    $scope.work = {};

    if (anchor != null) {
        $location.hash(anchor);
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

    $scope.chooseFile = function() {
        setTimeout(function() {
            angular.element("#work-for-us-file").trigger('click');
        }, 0);
    }

    $scope.send = function() {
        sweet.default("Nos enviarás un correo con tu información y tu mensaje", function() {
            let formData = {
                work: angular.copy($scope.work),
                file: angular.copy($scope.work.file)
            };

            delete formData.work.file;

            openRest("publicUser/sendWorkForUs").multipart(formData, function() {
                $scope.work = {};
                notif.success("Información enviada con éxito");
                $scope.work = {};
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
    }
});
