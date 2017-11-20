/*!
 * blogCtrl.js - v0.1
 * http://www.levelapsoftware.com
 * License: MIT
 * Requirements:
 * - RESTful Web Services
 * - openRest factory
 */
angular.module('LevelapBlog').controller('BlogCtrl', function($scope, $rootScope, $state, importantBlogs, categories, tags, blogsPreview, openRest, friendlyUrl, $location, $window) {
    importantBlogs.$promise.then(function(data) {
        $scope.importantBlogs = data.content;
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

    $scope.searchInBlog = function(text) {
        if (text == null) {
            text = '';
        }

        $state.go("levelapBlog.blog.search", {text: text});
    }

    $scope.getClipPath = function(id) {
        if (navigator.vendor.toLowerCase().indexOf("apple") < 0 && navigator.vendor.toLowerCase().indexOf("crios") < 0 && navigator.vendor.toLowerCase().indexOf("ipad") < 0 && navigator.vendor.toLowerCase().indexOf("iphone") < 0) {
            if (id == 'diamond') {
                return {
                    'clip-path': 'url(#diamond)',
                    '-webkit-clip-path': 'url(#diamond)'
                };
            }

            if (id == 'border') {
                return {
                    'clip-path': 'url(#border)',
                    '-webkit-clip-path': 'url(#border)'
                };
            }
        } else {
            if (id == 'diamond') {
                return {
                    'clip-path': 'polygon(50% 7%, 94% 50%, 50% 94%, 6% 50%)',
                    '-webkit-clip-path': 'polygon(50% 7%, 94% 50%, 50% 94%, 6% 50%)'
                };
            }

            /*if (id == 'border') {
                return {
                    'clip-path': 'polygon(50% 0%, 100% 50%, 50% 100%, 0% 50%)',
                    '-webkit-clip-path': 'polygon(50% 0%, 100% 50%, 50% 100%, 0% 50%)'
                };
            }*/
        }

        return {};
    }
});