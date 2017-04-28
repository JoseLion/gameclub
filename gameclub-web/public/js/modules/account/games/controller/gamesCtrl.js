angular.module('Games').controller('GamesCtrl', function($scope) {

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

});
