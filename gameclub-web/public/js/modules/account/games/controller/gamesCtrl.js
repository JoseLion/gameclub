angular.module('Games').controller('GamesCtrl', function($scope, isGameLoan, isEditing) {

    $scope.isSearching = false;
    $scope.showSearch = function() {
        $scope.isSearching = true;
    };

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
    $scope.background = {
        background: "url('img/test/cover-zelda.jpg') center bottom / 100% no-repeat"
    };
    $scope.isGameLoan = isGameLoan;
    $scope.isEditing = isEditing;
    $scope.gameLoan = {
        status: 'DISPONIBLE',
        gameStatus: 9,
        comments: 'El juego tiene un rasguño en el tiro del CD.',
        insured: true,
        coins: 150
    };


});
