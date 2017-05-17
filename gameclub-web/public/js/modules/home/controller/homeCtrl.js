angular.module('Home').controller('HomeCtrl', function($scope, $rootScope, $location, anchor, $state, friendlyUrl, sweet, openRest, notif, forEach, friendlyUrl) {
    $scope.search = {};
    $scope.contactUs = {};

    chooseFirstCategory(0);

    $scope.find = function() {
        $state.go("^.search", {
            name: $scope.search.name,
            categoryId: $scope.search.category != null ? $scope.search.category.id : null,
            consoleId: $scope.search.console.id,
            title: friendlyUrl(($scope.search.name != null ? ($scope.search.name.trim() + " ") : "") + $scope.search.console.name + ($scope.search.category != null ? (" " + $scope.search.category.name) : "") + " page " + ($scope.search.page != null ? $scope.search.page + 1 : 1))
        });
    }

    $scope.sendContactUs = function() {
        sweet.default("Nos enviará un correo con su mensaje e información", function() {
            openRest("publicUser/sendContactUs").post($scope.contactUs, function() {
                notif.success("El correo se envió con éxito");
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
    }

    $scope.categoryChoosen = function(index, categoryId) {
        forEach($rootScope.categories, function(category, i) {
            angular.element("#home-category-" + i).removeClass("active");
        });

        angular.element("#home-category-" + index).addClass("active");
        $scope.gamesByCat = [];
        $scope.catCounter = 0;

        openRest("game/findGamesByCategory/:categoryId", true).get({categoryId: categoryId}, function(data) {
            $scope.gamesByCat = data;
        });
    }

    $scope.getCategoryVector = function(category, index) {
        let element = angular.element("#home-category-" + index);

        if (element.hasClass("active")) {
            return category.whiteBase64;
        } else {
            return category.blackBase64;
        }
    }

    $scope.previousGameByCat = function() {
        if ($scope.catCounter == 0) {
            $scope.catCounter = $scope.gamesByCat.length - 1;
        } else {
            $scope.catCounter--;
        }
    }

    $scope.nextGameByCat = function() {
        if ($scope.catCounter == $scope.gamesByCat.length - 1) {
            $scope.catCounter = 0;
        } else {
            $scope.catCounter++;
        }
    }

    $scope.viewGame = function(game) {
        $state.go('gameclub.game', {id: game.id, name: friendlyUrl(game.name)});
    }

    function chooseFirstCategory(counter) {
        if ($rootScope.categories != null) {
            setTimeout(function() {
                angular.element("#category-icon-0").trigger("click");
            }, 0);
        } else {
            if (counter < 20) {
                counter++;

                setTimeout(function() {
                    $scope.$apply(function() {
                        chooseFirstCategory(counter);
                    });
                }, 500);
            }
        }
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
    };
    $scope.getNextGame = function() {
        let temp = $scope.mostPlayed.splice(-1, 1);
        $scope.mostPlayed.unshift(temp[0]);
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

    $scope.nameAutocomplete = [];
    $scope.$watch('search.name', function(newValue, oldValue) {
        if(newValue != null && newValue.length % 3 == 0) {
            openRest("game/findAutocomplete/:name", true).get({name: newValue}, function(data) {
                $scope.nameAutocomplete = data;
            });
        }
    });

});
