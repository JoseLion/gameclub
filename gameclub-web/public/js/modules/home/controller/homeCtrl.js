angular.module("Home").controller('HomeCtrl', function($scope) {

    $scope.type = {};
    $scope.gameConsole = {};
    $scope.currentBlogPage = 0;

    $scope.mostPlayed = [
        {
            id: 1,
            url: 'img/game-prueba-1.png',
            score: 4
        },
        {
            id: 2,
            url: 'img/game-prueba-2.png',
            score: 5
        },
        {
            id: 3,
            url: 'img/game-prueba-3.png',
            score: 4
        },
        {
            id: 4,
            url: 'img/game-prueba-4.png',
            score: 3
        }
    ];
    $scope.viewGameDetail = function(game) {
        console.log('SE DEBE HACER LA VISUALIZACIÓN DEL JUEGO');
    };

    $scope.categoryActive = 0;
    $scope.categories = [
        {
            id: 1,
            name: 'Action',
            logo: 'img/svg/sports.svg',
            active: true
        },
        {
            id: 2,
            name: 'Adventure',
            logo: 'img/svg/sports.svg',
            active: false
        },
        {
            id: 3,
            name: 'Shooter',
            logo: 'img/svg/sports.svg',
            active: false
        },
        {
            id: 4,
            name: 'Mass Multiplayer',
            logo: 'img/svg/sports.svg',
            active: false
        },
        {
            id: 5,
            name: 'Simulation',
            logo: 'img/svg/sports.svg',
            active: false
        },
        {
            id: 6,
            name: 'RPG',
            logo: 'img/svg/sports.svg',
            active: false
        },
        {
            id: 7,
            name: 'Strategy',
            logo: 'img/svg/sports.svg',
            active: false
        },
        {
            id: 8,
            name: 'Sports',
            logo: 'img/svg/sports.svg',
            active: false
        }
    ];
    $scope.categoryChoosen = function(category, idx) {
        $scope.categories[$scope.categoryActive].active = false;
        $scope.categoryActive = idx
        category.active = true;
    };

    $scope.blogs = [
        {
            blogId: 1,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/blog-preview-01.png',
            date: new Date('11-09-2016'),
            url: '#'
        },
        {
            blogId: 2,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/blog-preview-02.png',
            date: new Date('11-09-2016'),
            url: '#'
        },
        {
            blogId: 3,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/blog-preview-03.png',
            date: new Date('11-09-2016'),
            url: '#'
        },
        {
            blogId: 4,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/blog-preview-04.png',
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
