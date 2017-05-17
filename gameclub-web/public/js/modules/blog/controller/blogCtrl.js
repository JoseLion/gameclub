angular.module('Blog').controller('BlogCtrl', function($scope, idBlog) {

    $scope.prueba = 'NORMAL';
    if(idBlog != null) {
        $scope.prueba = 'TENGO SELECCIONADO EL BLOG';
    }

    $scope.blogs = [
        {
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
    ];

    $scope.importantBlogs = [
        {
            banner: {
                name: 'halo-5-wallpaper.png',
                src: 'img/test/blog-cover-01.png',
                title: 'HALO 5 Wallpaper'
            },
            title: 'LOREM IPSUM DOLOR SIT AMET',
            summary: 'onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur'
        }, {
            banner: {
                name: 'halo-5-wallpaper.png',
                src: 'img/test/blog-cover-01.png',
                title: 'HALO 5 Wallpaper'
            },
            title: 'LOREM IPSUM DOLOR SIT AMET',
            summary: 'onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur'
        }, {
            banner: {
                name: 'halo-5-wallpaper.png',
                src: 'img/test/blog-cover-01.png',
                title: 'HALO 5 Wallpaper'
            },
            title: 'LOREM IPSUM DOLOR SIT AMET',
            summary: 'onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore onsectetur'
        }
    ];

    $scope.categories = [
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
    ];

    $scope.tags = [
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
    ];

    $scope.blogsPreview = [
        {
            id: 1,
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
            id: 1,
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
            id: 1,
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
            id: 1,
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
    ];

    $scope.$watch('currentBlogPage', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            console.log('SE DEBE HACER LA CONSULTA PARA LLAMAR A MAS PREVIEWS');
        }
    });

});
