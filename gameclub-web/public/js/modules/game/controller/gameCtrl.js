angular.module('Game').controller('GameCtrl', function($scope, game, $state, Const, openRest, getImageBase64, $location, forEach, getImageBase64) {
    if (game != null) {
        game.$promise.then(function(data) {
            console.log("game: ", data);
            $scope.game = data;
            openRest("archive/downloadFile").download({name: $scope.game.banner.name, module: $scope.game.banner.module}, function(data) {
                $scope.background = {
                    background: "url('" + getImageBase64(data, $scope.game.banner.type) + "') center bottom / 100% no-repeat"
                };
            });

            forEach($scope.game.consoles, function(gameConsole) {
                openRest("archive/downloadFile").download({name: gameConsole.console.blackLogo.name, module: gameConsole.console.blackLogo.module}, function(data) {
    				gameConsole.console.blackLogoBase64 = getImageBase64(data, gameConsole.console.blackLogo.type);
    			});
            });

            if($scope.game.consoles != null) {
                $scope.search = {console: $scope.game.consoles[0]};
            }
        });
    } else {
        $state.go(Const.mainState);
    }

    $scope.addToLibrary = function() {
        $state.go("^.account.myGames", {game: $scope.game, consoleSelected: $scope.search.console});
    }

    $scope.login = function() {
        $state.go("^.login", {redirect: $location.$$absUrl});
    }

    $scope.consoleSelected = function() {
        console.log('FIND AVAILABLES BY CONSOLE: ', $scope.search);
    };

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

});
