angular.module('Search').controller('SearchCtrl', function($scope, games, search, $state, friendlyUrl) {
    $scope.search = search;
    console.log("search: ", search);
    $scope.totalElements;

    games.$promise.then(function(data) {
        setPagedData(data);
    });

    $scope.find = function() {
        $state.go("^.search", {search: $scope.search, title: friendlyUrl(($scope.search.name != null ? ($scope.search.name.trim() + " ") : "") + $scope.search.console.name + ($scope.search.category != null ? (" " + $scope.search.category.name) : "") + " page " + ($scope.search.page != null ? $scope.search.page + 1 : 1))});
    }

    $scope.pageChanged = function() {
        $scope.search.page = $scope.currentPage - 1;
        $scope.find();
    }

    function setPagedData(data) {
        $scope.games = data.content;
        $scope.totalElements = data.totalElements;
        $scope.totalPages = data.totalPages;
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

});
