angular.module('PublicProfile').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider

	.state(prefix + 'publicProfile', {
		url: '/publicProfile/:id/:alias',
		params: {id: null, alias: null},	
		templateUrl: 'js/modules/publicProfile/view/publicProfile.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: function(seoUser) {
				return seoUser.name + " " + seoUser.lastName + " GameClub";
			},
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila tus Juegos, Gana Dinero, Juega más pagando menos. Share and Play',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
		},
		controller: 'PublicProfileCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'PublicProfile',
					files: ['js/modules/publicProfile/controller/publicProfileCtrl.js', 'js/modules/publicProfile/style/publicProfile.less', 'js/modules/publicProfile/style/publicProfile.responsive.less']
				}]);
			},

			user: function(openRest, $stateParams) {
				return openRest("publicUser/findOne/:id").get({id: $stateParams.id});
			},

			reviews: function(rest, $stateParams) {
				return rest("review/getReviewsOfUser/:id/:page").get({id: $stateParams.id, page: 0});
			},

			seoUser: function(openRest, $stateParams) {
				return openRest("publicUser/findOne/:id").get({id: $stateParams.id}).$promise;
			}
		}
	});

});
