angular.module('Search').controller('SearchCtrl', function($scope, $rootScope, games, search, $state, friendlyUrl, getIndexOfArray, openRest) {
    $scope.search = {};
    $scope.totalPages;

    if (search != null) {
        setSerchFields();
    }

    games.$promise.then(function(data) {
        console.log(data)
        setPagedData(data);
    });

    $scope.find = function() {
        $state.go("^.search", {
            name: $scope.search.name != null ? $scope.search.name : "",
            categoryId: $scope.search.category != null ? $scope.search.category.id : null,
            consoleId: $scope.search.console.id,
            page: $scope.search.page,
            title: friendlyUrl(($scope.search.name != null ? ($scope.search.name.trim() + " ") : "") + $scope.search.console.name + ($scope.search.category != null ? (" " + $scope.search.category.name) : "") + " page " + ($scope.search.page != null ? $scope.search.page + 1 : 1))
        });
    }

    $scope.pageChanged = function() {
        $scope.search.page = $scope.currentPage;
        $scope.find();
    }

    function setPagedData(data) {
        $scope.games = data.content;
        $scope.totalPages = data.totalPages;
        $scope.currentPage = data.number;
    }

    function setSerchFields() {
        if ($rootScope.categories != null && $rootScope.consoles != null) {
            $scope.search.name = search.name;

            let index = getIndexOfArray($rootScope.categories, 'id', search.categoryId);
            $scope.search.category = $rootScope.categories[index];

            let index2 = getIndexOfArray($rootScope.consoles, 'id', search.consoleId);
            $scope.search.console = $rootScope.consoles[index2];

            $scope.search.page = search.page;
        } else {
            setTimeout(function() {
                $scope.$apply(function() {
                    setSerchFields();
                });
            }, 500);
        }
    }









    $scope.currentPage = 0;
    $scope.gameList = [
        {
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: Black Ops 3',
            coins: 150,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4.svg'
            }
        }, {
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: Black Ops 3 - Deluxe Edition',
            coins: 150,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4.svg'
            }
        }, {
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: MW3',
            coins: 150,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4.svg'
            }
        }, {
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: MW3 - Deluxe Edition',
            coins: 150,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4.svg'
            }
        }, {
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: Infinite Warfare',
            coins: 100,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4.svg'
            }
        }, {
            src: 'img/test/game-3.png',
            title: 'CALL OF DUTY: HEROES',
            coins: 70,
            rating: 4,
            types: [
                {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }, {
                    src: 'img/test/svg/sports-w.svg'
                }
            ],
            contentRating: {
                src: 'img/test/svg/esrb.svg'
            },
            platform: {
                src: 'img/test/svg/ps4.svg'
            }
        }
    ];

    $scope.viewGame = function(game) {
        console.log('DIRECCIONAR AL JUEGO');
    };

    $scope.nameAutocomplete = [];
    $scope.$watch('search.name', function(newValue, oldValue) {
        if(newValue != null && newValue.length % 3 == 0) {
            openRest("game/findAutocomplete/:name", true).get({name: newValue}, function(data) {
                $scope.nameAutocomplete = data;
            });
        }
    });

});
