angular.module('Home').controller('HomeCtrl', function($scope, $location, anchor, $state, friendlyUrl, sweet, openRest, notif) {
    $scope.search = {};
    $scope.contactUs = {};

    $scope.find = function() {
        $state.go("^.search", {search: $scope.search, title: friendlyUrl(($scope.search.name != null ? ($scope.search.name.trim() + " ") : "") + $scope.search.console.name + ($scope.search.category != null ? (" " + $scope.search.category.name) : "") + " page " + ($scope.search.page != null ? $scope.search.page + 1 : 1))});
    }

    $scope.sendMail = function() {
        sweet.default("Nos enviará un correo con su mensaje e información", function() {
            openRest("publicUser/contactUs").get(function() {
                notif.success("El correo se envió con éxito");
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
    }









    if (anchor != null) {
        $location.hash(anchor);
        //$anchorScroll.yOffset = angular.element('#fixedbar')[0].offsetHeight;
    }

    $scope.type = {};
    $scope.gameConsole = {};
    $scope.currentBlogPage = 0;

    $scope.mostPlayed = [
        {
            id: 1,
            url: 'img/test/game-1.png',
            score: 4
        },
        {
            id: 2,
            url: 'img/test/game-2.png',
            score: 4
        },
        {
            id: 3,
            url: 'img/test/game-3.png',
            score: 5
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

    $scope.categoryChoosen = function(category, idx) {
        $scope.categories[$scope.categoryActive].active = false;
        $scope.categoryActive = idx
        category.active = true;
    };

    // REMPLAZAR POR FUNCIONALIDAD CON SERVICIO WEB
    let counter = 2;
    $scope.gameByCat = $scope.mostPlayed[counter];
    $scope.previousGameByCat = function() {
        if(counter-- == 0) { counter += 4;}
        $scope.gameByCat = $scope.mostPlayed[counter];
        setTimeout(function() {
            $scope.$apply();
        }, 0);
    };
    $scope.nextGameByCat = function() {
        if(counter++ == 3) { counter -= 4; }
        $scope.gameByCat = $scope.mostPlayed[counter];
        setTimeout(function() {
            $scope.$apply();
        }, 0);
    };

    $scope.blogs = [
        {
            blogId: 1,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/test/blog-preview-01.png',
            date: new Date('11-09-2016'),
            url: '#'
        },
        {
            blogId: 2,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/test/blog-preview-02.png',
            date: new Date('11-09-2016'),
            url: '#'
        },
        {
            blogId: 3,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/test/blog-preview-03.png',
            date: new Date('11-09-2016'),
            url: '#'
        },
        {
            blogId: 4,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/test/blog-preview-04.png',
            date: new Date('11-09-2016'),
            url: '#'
        }
    ];

    $scope.$watch('currentBlogPage', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            console.log('SE DEBE HACER LA CONSULTA PARA LLAMAR A MAS PREVIEWS');
        }
    });

});
