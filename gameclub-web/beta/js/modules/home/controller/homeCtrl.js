angular.module('Home').controller('HomeCtrl', function($scope, $rootScope, provinces, $location, anchor, $state, friendlyUrl, sweet, openRest, notif, forEach, friendlyUrl, blogsPreview) {
    if (anchor != null) {
        $location.hash(anchor);
        //$anchorScroll.yOffset = angular.element('#fixedbar')[0].offsetHeight;
    }

    provinces.$promise.then(function(data) {
        $scope.provinces = data;
    });

    $scope.publicUser = {};
    $scope.saveSubscriber = function(form) {
        if (form.$valid == true) {
            sweet.default("Nos enviará un correo con su información", function() {
                $scope.publicUser.isSubscriber = true;

                openRest("publicUser/saveSubscriber").post($scope.publicUser, function() {
                    $scope.publicUser = {};
                    $scope.wasSubscribed = true;
                    sweet.close();
                }, function(error) {
                    notif.danger(error.data != null ? error.data.message : error);
                });
            });
        } else {
            if (form.$error.required) {
                notif.danger("Completa todos los campos correctamente para poder participar");
            }

            if (form.$error.email) {
                notif.danger("Ingresa un correo electrónico válido");
            }
        }
    }

    $scope.currentBlogPage = 0;

    blogsPreview.$promise.then(function(data) {
        setPageBlogsMostSeen(data);
    });

    $scope.provinceRemoved = function() {
        $scope.publicUser.location = null;
    }

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
                transform: 'translate(' + preview.squareCrop.a + 'px,' + preview.squareCrop.b + 'px)',
                zoom: (preview.squareCrop.c * 0.75)
            };
        });
        $scope.totalPagesMostSeen = data.totalPages;
    }

});
