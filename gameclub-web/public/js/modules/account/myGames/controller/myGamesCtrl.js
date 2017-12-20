angular.module('MyGames').controller('MyGamesCtrl', function($scope, $rootScope, gamesList, game, consoleSelected, integrity, mostPlayed, $state, notif, friendlyUrl, openRest, getImageBase64, sweet, rest, forEach, Const, shippingKitValue, $uibModal) {
    $scope.myGame = {};
    $scope.filter = {};
    $scope.search = {};


    $scope.priceChartingGM = parseFloat($rootScope.settings[Const.settings.priceChartingGames].value);
    $scope.priceChartingGMLoan = 0.0;
    $scope.gameLoanPCH = parseFloat($rootScope.settings[Const.settings.weekShippingCost].value);
    gamesList.$promise.then(function(data) {
        setPagedData(data);
    });

    integrity.$promise.then(function(data) {
        $scope.integrity = data;
    });

    mostPlayed.$promise.then(function(data) {
        $scope.mostPlayed = data;
    });

    shippingKitValue.$promise.then(function(data) {
        $scope.shippingKitValue = parseFloat(data.value);
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

            if ($scope.myGame.console.id == gameConsole.console.id) {
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
        let priceChartingGMLoan = 0.0;
        if($rootScope.settings[Const.settings.priceChartingGames].type == "percentage" && $scope.myGame.game.uploadPayment != null){
            priceChartingGMLoan = $scope.myGame.game.uploadPayment/$scope.gameLoanPCH;
        } else if($rootScope.settings[Const.settings.priceChartingGames].type == "number" && $scope.myGame.game.uploadPayment != null){
            priceChartingGMLoan = ($scope.myGame.game.uploadPayment+priceChartingGM)/gameLoanPCH;
        }

        let isValid = true;
        let minPrice = 0.0;
        let maxPrice = 0.0;
        if($rootScope.settings[Const.settings.priceChartingMin].type == 'percentage' && $rootScope.settings[Const.settings.priceChartingMax].type == 'percentage'){
           minPrice = priceChartingGMLoan-((priceChartingGMLoan*parseFloat($rootScope.settings[Const.settings.priceChartingMin].value))/100);
           maxPrice = priceChartingGMLoan+((priceChartingGMLoan*parseFloat($rootScope.settings[Const.settings.priceChartingMax].value))/100);
        } else if($rootScope.settings[Const.settings.priceChartingMin].type == 'number' && $rootScope.settings[Const.settings.priceChartingMax].type == 'number'){
           minPrice = priceChartingGMLoan-parseFloat($rootScope.settings[Const.settings.priceChartingMin].value);
           maxPrice = priceChartingGMLoan+parseFloat($rootScope.settings[Const.settings.priceChartingMax].value);
        }
        
        if($scope.myGame.cost < minPrice || $scope.myGame.cost > maxPrice ){
            isValid = false;
            sweet.error('El precio de alquiler de ' + $scope.myGame.game.name + ' debe ser entre $' + minPrice.toFixed(2) + ' y $' + maxPrice.toFixed(2));
        }
        
        if (isValid == true) {
            sweet.save(function() {
                $scope.myGame.console = $scope.search.console.console;

                rest("publicUser/saveGame").post($scope.myGame, function(data) {
                    sweet.success();
                    setPagedData(data);
                    $scope.search = {};
                    $scope.showGame = false;
                    sweet.close();

                    if ($scope.gamesList.length == 1) {
                        let modal = $uibModal.open({
                            size: 'md',
                            backdrop: 'static',
                            templateUrl: 'firstGameModal.html',
                            controller: function($scope, $uibModalInstance) {
                                $scope.ok = function() {
                                    $uibModalInstance.close();
                                }
                            },
                            resolve: {}
                        });

                        modal.result.then(function() {
                            $state.go("gameclub.account.messages");
                        }, function() {
                            $state.go("gameclub.account.messages");
                        });
                    }

                    rest("publicUser/getCurrentUser").get(function(data) {
                        $rootScope.currentUser = data;
                    });
                }, function(error) {
                    sweet.close();
                });
            });
        }
    }

    $scope.goBack = function() {
        $scope.showGame = false;
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

    $scope.mostPlayed = [
        {
            id: 1,
            url: 'img/test/game-1.png',
            rating: 4
        },
        {
            id: 2,
            url: 'img/test/game-2.png',
            rating: 4
        },
        {
            id: 3,
            url: 'img/test/game-3.png',
            rating: 5
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
    }

    $scope.getNextGame = function() {
        let temp = $scope.mostPlayed.splice(-1, 1);
        $scope.mostPlayed.unshift(temp[0]);
    }

    $scope.nameAutocomplete = [];
    $scope.$watch('search.name', function(newValue, oldValue) {
        if(newValue != null && newValue != ''  && newValue.length % 3 == 0) {
            openRest("game/findAutocomplete/:name", true).get({name: newValue}, function(data) {
                $scope.nameAutocomplete = data;
            });
        }
    });

    $scope.askShippingKit = function() {
        $state.go('gameclub.shippingKit');
    };

});
