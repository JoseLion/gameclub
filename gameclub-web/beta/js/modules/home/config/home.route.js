angular.module('Home').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'home', {
		url: '/home',
		params: {
			anchor: null
		},
		templateUrl: 'js/modules/home/view/home.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'GameClub - Alquila Videojuegos & Gana Dinero',
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila Juegos, Gana Dinero, PS4, Xbox, Nintendo.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',
			properties: {'og:title': 'GameClub - Alquila Videojuegos & Gana Dinero'}
		},
		controller: 'HomeCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Home',
					files: ['js/modules/home/controller/homeCtrl.js']
				}, {
					name: 'LevelapBlog',
					files: ['js/modules/levelapBlog/resources/blog.css', 'js/modules/levelapBlog/resources/mostSeen.js']
				}]);
			},

			anchor: function($stateParams) {
				return $stateParams.anchor;
			},

            blogsPreview: function(openRest) {
                return openRest("levelapBlog/findArticles").post({isMostSeen: true}, function(data) {
                    return data;
                });
            },

            provinces: function(openRest) {
				return openRest("location/findChildrenOf/:code", true).get({code: 'EC'}, function(data) {
					return data;
				});
			}
		}
	});

});
