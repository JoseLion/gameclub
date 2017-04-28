angular.module('Game').controller('GameCtrl', function($scope) {

    $scope.background = {
        background: "url('img/test/cover-zelda.jpg') center bottom / 100% no-repeat"
    };

    $scope.mostPlayed = [
        {
            id: 1,
            url: 'img/test/game-1.png',
            score: 4
        },
        {
            id: 2,
            url: 'img/test/game-2.png',
            score: 5
        },
        {
            id: 3,
            url: 'img/test/game-3.png',
            score: 4
        },
        {
            id: 4,
            url: 'img/test/game-4.png',
            score: 3
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
