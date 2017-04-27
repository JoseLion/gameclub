angular.module('Home').controller('HomeCtrl', function($scope, $location, anchor) {

    if (anchor != null) {
        $location.hash(anchor);
        //$anchorScroll.yOffset = angular.element('#fixedbar')[0].offsetHeight;
    }

    $scope.gameConsoles =[
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

    $scope.categoryActive = 0;
    $scope.categories = [
        {
            id: 1,
            name: 'Action',
            logo: 'img/test/svg/sports.svg',
            active: true
        },
        {
            id: 2,
            name: 'Adventure',
            logo: 'img/test/svg/sports.svg',
            active: false
        },
        {
            id: 3,
            name: 'Shooter',
            logo: 'img/test/svg/sports.svg',
            active: false
        },
        {
            id: 4,
            name: 'Mass Multiplayer',
            logo: 'img/test/svg/sports.svg',
            active: false
        },
        {
            id: 5,
            name: 'Simulation',
            logo: 'img/test/svg/sports.svg',
            active: false
        },
        {
            id: 6,
            name: 'RPG',
            logo: 'img/test/svg/sports.svg',
            active: false
        },
        {
            id: 7,
            name: 'Strategy',
            logo: 'img/test/svg/sports.svg',
            active: false
        },
        {
            id: 8,
            name: 'Sports',
            logo: 'img/test/svg/sports.svg',
            active: false
        }
    ];
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
