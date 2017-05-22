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
        url: '',
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
                        baseSrc.concat('resources/articlePreview.js'),
                        baseSrc.concat('resources/mostSeen.js'),
                        baseSrc.concat('resources/blogNavigation.js')
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
        url: '/blog',
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
        url: '/blog/:id/:title',
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

            article: function($stateParams, $q) {
                var deferred = $q.defer();
                setTimeout(function() {
                    deferred.resolve(
                        {
                            id: $stateParams.id,
                            banner: {
                                name: 'halo-5-wallpaper.png',
                                src: 'img/test/blog-cover-01.png',
                                title: 'HALO 5 Wallpaper'
                            },
                            creationDate: new Date('11-05-2017'),
                            title: 'Algunos tipos para superar Halo 5...',
                            author: 'Game Club',
                            tags: [
                                {
                                    name: 'Juegos XBOX'
                                }, {
                                    name: 'Más jugados'
                                }, {
                                    name: 'Games PS4'
                                }
                            ],
                            body: `
"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commo-
do consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commo-
do consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est labo-
rum.""Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est
laborum.""Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex
ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est
laborum.""Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore
et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex
ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est
laborum."
                            `
                        }
                    );
                }, 100);
                deferred.$promise = deferred.promise;
                return deferred;
            },

            comments: function($stateParams, $q) {
                var deferred = $q.defer();
                setTimeout(function() {
                    deferred.resolve(
                        {
                            content: [
                                {
                                    blogArticle: {
                                        id: 1,
                                    },
                                    username: 'usuario1',
                                    comment: 'Comentario 1',
                                    level: 0
                                }, {
                                    blogArticle: {
                                        id: 2,
                                    },
                                    username: 'usuario2',
                                    comment: 'Comentario 2Comentario 2Comentario 2',
                                    level: 0
                                }, {
                                    blogArticle: {
                                        id: 3,
                                    },
                                    username: 'usuario3',
                                    comment: 'Comentario 3',
                                    level: 0
                                }
                            ],
                            last: false
                        }
                    );
                }, 500);
                deferred.$promise = deferred.promise;
                return deferred;
            }

        }
    });

}).constant('BlogConst', {

    commentsLevel: 1

}).run(function($rootScope, BlogConst) {
    $rootScope.BlogConst = BlogConst;
});
