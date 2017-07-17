/*!
 * levelapBlog.js - v0.1
 * http://www.levelapsoftware.com
 * License: MIT
 * Requirements:
 * - RESTful Web Services
 * - openRest factory
 * Using plugin:
 * - You must add the module 'LevelapBlog'
 */
angular.module('LevelapBlog', []).config(function($stateProvider) {

    let prefix = 'levelapBlog.';
    let baseSrc;
    for (let i = document.getElementsByTagName("script").length - 1; i >= 0; i--) {
        let script = angular.element(document.getElementsByTagName("script")[i]);
        if (script.attr("src") != null && script.attr("src").indexOf("levelapBlog.js") > -1) {
            baseSrc = script.attr("src").substring(0, script.attr("src").indexOf("levelapBlog.js"));
            break;
        }
    }
    $stateProvider
    .state(prefix + 'blog', {
        url: '/blog',
        templateUrl: baseSrc.concat('view/blogTemplate.html'),
        data: {displayName: 'Blog', description: '', keywords: ''},
        controller: 'BlogCtrl',
        resolve: {
            loadPlugin: function($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    name: 'LevelapBlog',
                    files: [
                        baseSrc.concat('controller/blogCtrl.js'),
                        baseSrc.concat('resources/blog.css'),
                        baseSrc.concat('resources/articleComments.js'),
                        baseSrc.concat('resources/articleCommentsForm.js'),
                        baseSrc.concat('resources/articlePreview.js'),
                        baseSrc.concat('resources/mostSeen.js'),
                        baseSrc.concat('resources/blogNavigation.js'),
                        baseSrc.concat('resources/ngXlinkHref.js')
                    ]
                }]);
            },
            importantBlogs: function(openRest) {
                return openRest("levelapBlog/findArticles").post({isFeatured: true}, function(data) {
                    return data;
                });
            },
            categories: function(openRest) {
                return openRest("levelapBlog/getCategories", true).get(function(data) {
                    return data;
                });
            },
            tags: function(openRest) {
                return openRest("levelapBlog/getTags", true).get(function(data) {
                    return data;
                });
            },
            blogsPreview: function(openRest) {
                return openRest("levelapBlog/findArticles").post({isMostSeen: true}, function(data) {
                    return data;
                });
            }
        }
    })
    .state(prefix + 'blog.home', {
        url: '/home',
        templateUrl: baseSrc.concat('view/blogHome.html'),
        data: {displayName: 'Blog', description: '', keywords: ''},
        controller: 'BlogHomeCtrl',
        resolve: {
            loadPlugin: function($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    name: 'LevelapBlog',
                    files: [
                        baseSrc.concat('controller/blogHomeCtrl.js')
                    ]
                }]);
            },
            blogs: function(openRest) {
                return openRest("levelapBlog/findArticles").post({isHomePage: true}, function(data) {
                    return data;
                });
            }
        }
    })
    .state(prefix + 'blog.detail', {
        url: '/detail/:id/:title',
        params: {
            id: null,
            title: null
        },
        templateUrl: baseSrc.concat('view/blogDetail.html'),
        data: {displayName: 'Blog', description: '', keywords: ''},
        controller: 'BlogDetailCtrl',
        resolve: {
            loadPlugin: function($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    name: 'LevelapBlog',
                    files: [
                        baseSrc.concat('controller/blogDetailCtrl.js')
                    ]
                }]);
            },
            article: function($stateParams, openRest) {
                return openRest("levelapBlog/findOne/:id").get({id: $stateParams.id}, function(data) {
                    return data;
                });
            },
            comments: function($stateParams, openRest) {
                return openRest("levelapBlog/getCommentsOf/:articleId/:page").get({articleId: $stateParams.id, page: 0}, function(data) {
                    return data;
                });
            }
        }
    })
    .state(prefix + 'blog.search', {
        url: '/search/:text',
        params: {
            text: null
        },
        templateUrl: baseSrc.concat('view/blogSearch.html'),
        data: {displayName: 'Blog', description: '', keywords: ''},
        controller: 'BlogSearchCtrl',
        resolve: {
            loadPlugin: function($ocLazyLoad) {
                return $ocLazyLoad.load([{
                    name: 'LevelapBlog',
                    files: [
                        baseSrc.concat('controller/blogSearchCtrl.js')
                    ]
                }]);
            },
            articles: function($stateParams, openRest) {
                return openRest("levelapBlog/findArticles").post({isSearch: true, text: $stateParams.text}, function(data) {
                    return data;
                });
            },
            searchValue: function($stateParams) {
                return $stateParams.text;
            }
        }
    });
}).constant('BlogConst', {
    commentsLevel: 0,
    messages: {
        required: 'Debe completar los campos marcados con *',
        emailValidation: 'Debe ingresar un email válido'
    },
    imageBaseUrl: 'img/gameclub/BlogArticle/'
}).run(function($rootScope, BlogConst) {
    $rootScope.BlogConst = BlogConst;
});
