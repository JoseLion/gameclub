angular.module('Game').controller('GameCtrl', function($scope, $rootScope, game, $state, Const, openRest, getImageBase64, $location, forEach, getImageBase64, notif) {
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
        if (getInfoPercentage() >= 100 && getIdentityPercentage() >= 100) {
            $state.go("^.account.myGames", {game: $scope.game, consoleSelected: $scope.search.console});
        } else {
            if (getInfoPercentage() < 100 && getIdentityPercentage() < 100) {
                notif.danger("Primero debes completar tu información de contacto y verificar tu identidad para cargar juegos a tu perfil");
            } else  {
                if (getInfoPercentage() < 100 && getIdentityPercentage() >= 100) {
                    notif.danger("Primero debes completar tu información de contacto para cargar juegos a tu perfil");
                }

                if (getInfoPercentage() >= 100 && getIdentityPercentage() < 100) {
                    notif.danger("Primero debes verificar tu identidad para cargar juegos a tu perfil");
                }
            }
        }
    }

    $scope.login = function() {
        $state.go("^.login", {redirect: $location.$$absUrl});
    }

    $scope.consoleSelected = function() {
        console.log('FIND AVAILABLES BY CONSOLE: ', $scope.search);
    }

    $scope.getPreviousGame = function() {
        let temp = $scope.mostPlayed.splice(0, 1);
        $scope.mostPlayed[3] = temp[0];
    }

    $scope.getNextGame = function() {
        let temp = $scope.mostPlayed.splice(-1, 1);
        $scope.mostPlayed.unshift(temp[0]);
    }

    function getInfoPercentage() {
        let percent = 0;
        let factor = 100 / 6;

        if ($rootScope.currentUser != null) {
            if ($rootScope.currentUser.document != null) {
                percent += factor;
            }
            if ($rootScope.currentUser.birthDate != null) {
                percent += factor;
            }
            if ($rootScope.currentUser.profession != null) {
                percent += factor;
            }
            if ($rootScope.currentUser.location != null) {
                percent += factor;
            }
            if ($rootScope.currentUser.billingAddress != null) {
                percent += factor;
            }
            if ($rootScope.currentUser.contactPhone != null) {
                percent += factor;
            }
        }
        
        percent = Math.round(percent);
        return percent;
    }

    function getIdentityPercentage() {
        let percent = 0;
        if ($rootScope.currentUser != null) {
            percent += $rootScope.currentUser.token == null ? 100 : 0;
            percent = Math.round(percent);
        }
        return percent;
    }

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
