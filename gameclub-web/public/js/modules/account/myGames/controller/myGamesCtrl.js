angular.module('MyGames').controller('MyGamesCtrl', function($scope, gamesList, game, $state, notif, friendlyUrl, openRest, getImageBase64, sweet, rest) {
    $scope.myGame = {};
    $scope.search = {};
    $scope.totalElements;

    gamesList.$promise.then(function(data) {
        setPagedData(data);
    });

    if (game != null) {
        $scope.showGame = true;
        $scope.myGame.game = game;
        $scope.myGame.console = game.consoles[0].console;

        openRest("archive/downloadFile").download({name: $scope.myGame.game.banner.name, module: $scope.myGame.game.banner.module}, function(data) {
            $scope.background = {
                background: "url('" + getImageBase64(data, $scope.myGame.game.banner.type) + "') center bottom / 100% no-repeat"
            };
        });
    }

    $scope.showSearch = function() {
        $scope.isSearching = true;
    }

    $scope.find = function() {
        if ($scope.search.console != null) {
            $state.go("gameclub.search", {
                name: $scope.search.name != null ? $scope.search.name : "",
                categoryId: $scope.search.category != null ? $scope.search.category.id : null,
                consoleId: $scope.search.console.id,
                title: friendlyUrl(($scope.search.name != null ? ($scope.search.name.trim() + " ") : "") + $scope.search.console.name + ($scope.search.category != null ? (" " + $scope.search.category.name) : "") + " page " + ($scope.search.page != null ? $scope.search.page + 1 : 1))
            });
        } else {
            notif.warning("La consola es requerida para realizar la búsqueda");
        }
    }

    $scope.save = function() {
        sweet.save(function() {
            rest("publicUser/saveGame").post($scope.myGame, function(data) {
                sweet.success();
                setPagedData(data);
                $scope.showGame = false;
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
    }

    function setPagedData(data) {
        $scope.gamesList = data.content;
        $scope.totalElements = data.totalElements;
        $scope.totalPages = data.totalPages;
    }








    $scope.gameConsolesW =[
        {
            name: 'PlayStation 4',
            img: 'img/test/svg/ps4.svg'
        }, {
            name: 'XBOX ONE',
            img: 'img/test/svg/xbox-one.svg'
        }, {
            name: 'Nintendo Switch',
            img: 'img/test/svg/nintendo-switch.svg'
        }
    ]

    $scope.gameConsole = {};
    $scope.gameConsoles =[
        {
            name: 'PlayStation 4',
            img: 'img/test/svg/ps4-b.svg'
        }, {
            name: 'XBOX ONE',
            img: 'img/test/svg/xbox-one-b.svg'
        }, {
            name: 'Nintendo Switch',
            img: 'img/test/svg/nintendo-switch-b.svg'
        }
    ];

    $scope.currentPage = 0;
    $scope.gameList = [
        {
            id: 1,
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: Black Ops 3',
            coins: 150,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4-b.svg'
            }
        }, {
            id: 2,
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: Black Ops 3 - Deluxe Edition',
            coins: 150,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4-b.svg'
            }
        }, {
            id: 3,
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: MW3',
            coins: 150,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4-b.svg'
            }
        }, {
            id: 4,
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: MW3 - Deluxe Edition',
            coins: 150,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4-b.svg'
            }
        }, {
            id: 5,
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: Infinite Warfare',
            coins: 100,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4-b.svg'
            }
        }, {
            id: 6,
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: HEROES',
            coins: 70,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }, {
                    src: 'img/test/svg/sports.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4-b.svg'
            }
        }
    ];

    $scope.viewGame = function(game) {
        console.log('DIRECCIONAR AL JUEGO');
    };

    /* MAQUETACIÓN DE LA FICHA DE PRESTAMO */

    $scope.gameLoan = {
        status: 'DISPONIBLE',
        gameStatus: 9,
        comments: 'El juego tiene un rasguño en el tiro del CD.',
        insured: true,
        coins: 150
    };


});
