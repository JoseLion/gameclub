angular.module('Game').controller('GameCtrl', function($scope, game, $state, Const, openRest, getImageBase64, $location) {
    if (game != null) {
        game.$promise.then(function(data) {
            $scope.game = data;

            openRest("archive/downloadFile").download({name: $scope.game.banner.name, module: $scope.game.banner.module}, function(data) {
                $scope.background = {
                    background: "url('" + getImageBase64(data, $scope.game.banner.type) + "') center bottom / 100% no-repeat"
                };
            });
        });
    } else {
        $state.go(Const.mainState);
    }

    $scope.addToLibrary = function() {
        $state.go("^.account.myGames", {game: $scope.game});
    }

    $scope.login = function() {
        $state.go("^.login", {redirect: $location.$$absUrl});
    }








    $scope.mostPlayed = [
        {
            id: 1,
            url: 'img/test/game-1.png',
            rating: 4
        },
        {
            id: 2,
            url: 'img/test/game-2.png',
            rating: 5
        },
        {
            id: 3,
            url: 'img/test/game-3.png',
            rating: 4
        },
        {
            id: 4,
            url: 'img/test/game-4.png',
            rating: 3
        }
    ];
    $scope.getPreviousGame = function() {
        let temp = $scope.mostPlayed.splice(0, 1);
        $scope.mostPlayed[3] = temp[0];
    };
    $scope.getNextGame = function() {
        let temp = $scope.mostPlayed.splice(-1, 1);
        $scope.mostPlayed.unshift(temp[0]);
    };

    $scope.availables = [
        {
            available: true,
            shipping: 2,
            city: 'Quito',
            coins: 100,
            rating: 5,
            gameStatus: 9
        }, {
            available: true,
            shipping: 2,
            city: 'Quito',
            coins: 150,
            rating: 2,
            gameStatus: 7
        }, {
            available: false,
            shipping: 5,
            city: 'Cuenca',
            coins: 100,
            rating: 5,
            gameStatus: 4
        }, {
            available: false,
            shipping: 5,
            city: 'Guayaquil',
            coins: 100,
            rating: 3,
            gameStatus: 10
        }
    ];

    $scope.gameConsoles =[
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
    ];

});
