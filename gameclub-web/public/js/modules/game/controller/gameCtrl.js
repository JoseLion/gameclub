angular.module('Game').controller('GameCtrl', function($scope, $rootScope, game, consoleId, availableGames, $state, Const, openRest, getImageBase64, $location, forEach, getImageBase64, notif) {
    if (game != null) {
        game.$promise.then(function(data) {
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

            $scope.console = {};
            if (consoleId != null) {
                forEach($scope.game.consoles, function(cross) {
                    if (cross.console.id == consoleId) {
                        $scope.console.selected = cross;
                        return 'break';
                    }
                });
            } else {
                $scope.console.selected = $scope.game.consoles[0];
            }
        });

        availableGames.$promise.then(function(data) {
            setPagedAvailableGames(data);
        });

        $scope.filters = [
            {icon: 'gc-send', sort: 'status', desc: true, active: false},
            {icon: 'gc-address', sort: 'publicUser.location.name', desc: true, active: false},
            {icon: 'gc-usd', sort: 'cost', desc: true, active: false},
            {icon: 'gc-rating', sort: 'publicUser.rating', desc: true, active: false},
            {icon: 'gc-cd', sort: 'integrity', desc: true, active: false},
            {icon: 'gc-filter', sort: ''}
        ];
    } else {
        $state.go(Const.mainState);
    }

    $scope.addToLibrary = function() {
        if (getInfoPercentage() >= 100 && getIdentityPercentage() >= 100) {
            $state.go("^.account.myGames", {game: $scope.game, consoleSelected: $scope.console.selected});
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
        console.log('FIND AVAILABLES BY CONSOLE: ', $scope.console.selected);
    }

    $scope.getPreviousGame = function() {
        let temp = $scope.mostPlayed.splice(0, 1);
        $scope.mostPlayed[3] = temp[0];
    }

    $scope.getNextGame = function() {
        let temp = $scope.mostPlayed.splice(-1, 1);
        $scope.mostPlayed.unshift(temp[0]);
    }

    $scope.openLoanView = function(cross) {
        if ($rootScope.currentUser != null) {
            $scope.loanGame = cross;
            $scope.loanViewOpen = true;

            $scope.loan = {
                weeks: 1,
                address: $rootScope.currentUser.billingAddress,
                rceiver: $rootScope.currentUser.rceiver
            };
            
        } else {
            $state.go("^.login", {redirect: $location.$$absUrl});
        }
    }

    $scope.doFilter = function(filter) {
        forEach($scope.filters, function(fltr) {
            if (fltr.icon == filter.icon && fltr.active == true) {
                filter.desc = !filter.desc;
            }

            fltr.active = false;
        });

        if (filter.sort != '') {
            filter.active = true;
        }
        
        filterAvailibleGames();
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

    function setPagedAvailableGames(data) {
        console.log("data: ", data);
        $scope.availableGames = data.content;
        $scope.lastPage = data.last;
        $scope.currentPage = data.number;
    }

    function filterAvailibleGames() {
        let activeFilter;
        forEach($scope.filters, function(filter) {
            if (filter.active == true) {
                activeFilter = filter;
                return 'break';
            }
        });

        let filter = {
            gameId: $scope.game.id,
            consoleId: $scope.console.selected.console.id,
            sort: activeFilter != null ? activeFilter.sort : '',
            desc: activeFilter != null ? activeFilter.desc : true,
            page: $scope.currentPage
        };

        openRest("game/getAvailableGames").post(filter, function(data) {
            setPagedAvailableGames(data);
        });
    }
});
