angular.module('MyGames').controller('MyGamesCtrl', function($scope, $rootScope, gamesList, game, consoleSelected, integrity, gamesSummary, mostPlayed, $state, notif, friendlyUrl, openRest, getImageBase64, sweet, rest, forEach, Const) {
    $scope.myGame = {};
    $scope.filter = {};
    $scope.search = {};

    gamesList.$promise.then(function(data) {
        setPagedData(data);
    });

    integrity.$promise.then(function(data) {
        $scope.integrity = data;
    });

    gamesSummary.$promise.then(function(data) {
        $scope.summary = data;
    });

    mostPlayed.$promise.then(function(data) {
        $scope.mostPlayed = data;
    });

    if (game != null) {
        $scope.myGame.game = game;

        openRest("archive/downloadFile").download({name: $scope.myGame.game.banner.name, module: $scope.myGame.game.banner.module}, function(data) {
            $scope.background = {
                background: "url('" + getImageBase64(data, $scope.myGame.game.banner.type) + "') center bottom / 100% no-repeat"
            };
        });

        forEach($scope.myGame.game.consoles, function(gameConsole) {
            openRest("archive/downloadFile").download({name: gameConsole.console.blackLogo.name, module: gameConsole.console.blackLogo.module}, function(data) {
                gameConsole.console.blackLogoBase64 = getImageBase64(data, gameConsole.console.blackLogo.type);
            });
            
            if(consoleSelected == gameConsole){
                $scope.search = {console: gameConsole};
            }
        });

        $scope.showGame = true;
    }

    $scope.editGame = function(cross) {
        $scope.myGame = cross;
        openRest("archive/downloadFile").download({name: $scope.myGame.game.banner.name, module: $scope.myGame.game.banner.module}, function(data) {
            $scope.background = {
                background: "url('" + getImageBase64(data, $scope.myGame.game.banner.type) + "') center bottom / 100% no-repeat"
            };
        });
        forEach($scope.myGame.game.consoles, function(gameConsole) {
            openRest("archive/downloadFile").download({name: gameConsole.console.blackLogo.name, module: gameConsole.console.blackLogo.module}, function(data) {
                gameConsole.console.blackLogoBase64 = getImageBase64(data, gameConsole.console.blackLogo.type);
            });
            if($scope.myGame.console.id == gameConsole.console.id){
                $scope.search = {console: gameConsole};
            }
        });
        $scope.showGame = true;
    }

    $scope.showSearch = function() {
        $scope.isSearching = true;
    }

    $scope.find = function() {
        if ($scope.search.console == null) {
            notif.danger(Const.errorMessages.consoleRequired);
        } else {
            $state.go("gameclub.search", {
                name: $scope.search.name != null ? $scope.search.name : "",
                categoryId: $scope.search.category != null ? $scope.search.category.id : null,
                consoleId: $scope.search.console.id,
                title: friendlyUrl(($scope.search.name != null ? ($scope.search.name.trim() + " ") : "") + $scope.search.console.name + ($scope.search.category != null ? (" " + $scope.search.category.name) : "") + " page " + ($scope.search.page != null ? $scope.search.page + 1 : 1))
            });
        }
    }

    $scope.save = function() {
        sweet.save(function() {
            $scope.myGame.console = $scope.search.console.console;
            rest("publicUser/saveGame").post($scope.myGame, function(data) {
                sweet.success();
                setPagedData(data);
                $scope.showGame = false;
                sweet.close();

                rest("publicUser/getCurrentUser").get(function(data) {
                    $rootScope.currentUser = data;
                });
            }, function(error) {
                sweet.close();
            });
        });
    }

    $scope.pageChanged = function() {
        filter();
    }

    $scope.consoleSelected = function() {
        filter();
    }

    $scope.sortGames = function(sort) {
        $scope.filter.sort = sort;
        filter();
    }

    $scope.goToTerms = function() {
        window.open($state.href('gameclub.termsConditions'), '_blank');
    }

    function setPagedData(data) {
        $scope.gamesList = data.content;
        $scope.totalPages = data.totalPages;
        $scope.filter.page = data.number;
    }

    function filter() {
        let filter = angular.copy($scope.filter);
        filter.console = null;

        if ($scope.filter.console != null) {
            filter.consoleId = $scope.filter.console.id;
        }

        rest("publicUser/getGamesList").post(filter, function(data) {
            setPagedData(data);
        });
        $scope.isConsoleFilter = true;
    }

    $scope.getPreviousGame = function() {
        let temp = $scope.mostPlayed.splice(0, 1);
        $scope.mostPlayed[3] = temp[0];
    };
    $scope.getNextGame = function() {
        let temp = $scope.mostPlayed.splice(-1, 1);
        $scope.mostPlayed.unshift(temp[0]);
    };

    $scope.nameAutocomplete = [];
    $scope.$watch('search.name', function(newValue, oldValue) {
        if(newValue != null && newValue != ''  && newValue.length % 3 == 0) {
            openRest("game/findAutocomplete/:name", true).get({name: newValue}, function(data) {
                $scope.nameAutocomplete = data;
            });
        }
    });

});
