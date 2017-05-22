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
                        baseSrc.concat('resources/mostSeen.js')
                    ]
                }]);
            },

            importantBlogs: function($q) {
                var deferred = $q.defer();
                setTimeout(function() {
                    deferred.resolve(
                        [
                            {
                                id: 1,
                                banner: {
                                    name: 'halo-5-wallpaper.png',
                                    src: 'img/test/blog-cover-01.png',
                                    title: 'HALO 5 Wallpaper'
                                },
                                title: 'LOREM IPSUM DOLOR SIT AMET',
                                summary: 'onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur'
                            }, {
                                id: 2,
                                banner: {
                                    name: 'halo-5-wallpaper.png',
                                    src: 'img/test/blog-cover-01.png',
                                    title: 'HALO 5 Wallpaper'
                                },
                                title: 'LOREM IPSUM DOLOR SIT AMET',
                                summary: 'onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur'
                            }, {
                                id: 3,
                                banner: {
                                    name: 'halo-5-wallpaper.png',
                                    src: 'img/test/blog-cover-01.png',
                                    title: 'HALO 5 Wallpaper'
                                },
                                title: 'LOREM IPSUM DOLOR SIT AMET',
                                summary: 'onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur'
                            }
                        ]
                    );
                }, 2500);
                deferred.$promise = deferred.promise;
                return deferred;
            },

            categories: function($q) {
                var deferred = $q.defer();
                setTimeout(function() {
                    deferred.resolve(
                        [
                            {
                                text: 'Categoría 1'
                            }, {
                                text: 'Categoría 2'
                            }, {
                                text: 'Categoría 3'
                            }, {
                                text: 'Categoría 4'
                            }, {
                                text: 'Categoría 5'
                            }, {
                                text: 'Categoría 6'
                            }
                        ]
                    );
                }, 500);
                deferred.$promise = deferred.promise;
                return deferred;
            },

            tags: function($q) {
                var deferred = $q.defer();
                setTimeout(function() {
                    deferred.resolve(
                        [
                            {
                                text: 'Juegos XBOX'
                            }, {
                                text: 'Games PS4'
                            }, {
                                text: 'Más jugados'
                            }, {
                                text: 'Video games'
                            }, {
                                text: 'Nintendo WII'
                            }, {
                                text: 'Games PSP'
                            }
                        ]
                    );
                }, 500);
                deferred.$promise = deferred.promise;
                return deferred;
            },

            blogsPreview: function($q) {
                var deferred = $q.defer();
                setTimeout(function() {
                    deferred.resolve(
                        [
                            {
                                id: 4,
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
                                summary: '"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."'
                            }, {
                                id: 5,
                                banner: {
                                    name: 'halo-5-wallpaper.png',
                                    src: 'img/test/blog-cover-01.png',
                                    title: 'HALO 5 Wallpaper'
                                },
                                creationDate: new Date('11-09-2017'),
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
                                summary: '"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."'
                            }, {
                                id: 6,
                                banner: {
                                    name: 'halo-5-wallpaper.png',
                                    src: 'img/test/blog-cover-01.png',
                                    title: 'HALO 5 Wallpaper'
                                },
                                creationDate: new Date('11-09-2017'),
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
                                summary: '"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."'
                            }, {
                                id: 7,
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
                                summary: '"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."'
                            }
                        ]
                    );
                }, 5000);
                deferred.$promise = deferred.promise;
                return deferred;
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

            blogs: function($q) {
                var deferred = $q.defer();
                setTimeout(function() {
                    deferred.resolve(
                        [
                            {
                                id: 101,
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
                                summary: '"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."'
                            }, {
                                id: 102,
                                banner: {
                                    name: 'ea-sports.png',
                                    src: 'img/test/blog-cover-02.png',
                                    title: 'EA Sports Wallpaper'
                                },
                                creationDate: new Date('11-09-2017'),
                                title: 'Muchos gamers opinan lo mismo...',
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
                                summary: '"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."'
                            }
                        ]
                    );
                }, 2500);
                deferred.$promise = deferred.promise;
                return deferred;
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
