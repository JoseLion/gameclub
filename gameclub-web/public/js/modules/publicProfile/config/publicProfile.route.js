angular.module('PublicProfile').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider

	.state(prefix + 'publicProfile', {
		url: '/publicProfile/:id/:alias',
		params: {id: null, alias: null},	
		templateUrl: 'js/modules/publicProfile/view/publicProfile.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'GameClub - Alquila Videojuegos & Gana Dinero',
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila Juegos, Gana Dinero, PS4, Xbox, Nintendo.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',
			properties: {'og:title': 'GameClub - Alquila Videojuegos & Gana Dinero'}
		},
		controller: 'PublicProfileCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'PublicProfile',
					files: ['js/modules/publicProfile/controller/publicProfileCtrl.js', 'js/modules/publicProfile/style/publicProfile.less', 'js/modules/publicProfile/style/publicProfile.responsive.less']
				}]);
			},

			user: function(rest, $stateParams) {
				return rest("publicUser/findOne/:id").get({id: $stateParams.id});
			},

			reviews: function(rest, $stateParams) {
				return rest("review/getReviewsOfUser/:id/:page").get({id: $stateParams.id, page: 0});
			}
		}
	});

});
