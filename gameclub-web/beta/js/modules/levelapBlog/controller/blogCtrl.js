/*!
 * blogCtrl.js - v0.1
 * http://www.levelapsoftware.com
 * License: MIT
 * Requirements:
 * - RESTful Web Services
 * - openRest factory
 */
angular.module('LevelapBlog').controller('BlogCtrl', function($scope, $rootScope, $state, importantBlogs, categories, tags, blogsPreview, openRest, friendlyUrl) {
    importantBlogs.$promise.then(function(data) {
        $scope.importantBlogs = data.content;
        $scope.importantBlogs.forEach(function(preview) {
            if(preview.diamondCrop != null) {
                preview.crop = {
                    transform: 'translate(' + preview.diamondCrop.a + 'px,' + preview.diamondCrop.b + 'px)',
                    zoom: (preview.diamondCrop.c / 2)
                };
            }
        });
    });
    
    categories.$promise.then(function(data) {
        $scope.categories = data;
    });

    tags.$promise.then(function(data) {
        $scope.tags = data;
    });

    blogsPreview.$promise.then(function(data) {
        setPageMostSeen(data);
    });

    $scope.searchArticles = function() {
        $state.go('levelapBlog.blog.search', {text: $scope.search == null ? '' : $scope.search.value});
    };

    $scope.$watch('currentPageMostSeen', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            openRest("levelapBlog/findArticles").post({isMostSeen: true, page: newValue}, function(data) {
                setPageMostSeen(data);
            });
        }
    });

    function setPageMostSeen(data) {
        $scope.blogsPreview = data.content;
        $scope.totalPagesMostSeen = data.totalPages;
    }

    if ($state.current.name == 'levelapBlog.blog') {
        $state.go('levelapBlog.blog.home');
    }

    let baseSrc;
    for (let i = document.getElementsByTagName("script").length - 1; i >= 0; i--) {
        let script = angular.element(document.getElementsByTagName("script")[i]);
        if (script.attr("src") != null && script.attr("src").indexOf("levelapBlog.js") > -1) {
            baseSrc = script.attr("src").substring(0, script.attr("src").indexOf("levelapBlog.js"));
            break;
        }
    }

    $scope.diamondBorderImg = baseSrc + "resources/img/diamond-border.svg";

    $scope.goToDetails = function(article) {
        $state.go("levelapBlog.blog.detail", {id: article.id, title: friendlyUrl(article.title)})
    }
});
