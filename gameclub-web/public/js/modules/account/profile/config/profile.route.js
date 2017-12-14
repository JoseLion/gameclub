angular.module('Profile').config(function($stateProvider) {

	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'profile', {
		url: '/profile',
		templateUrl: 'js/modules/account/profile/view/profile.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'GameClub - Alquila Videojuegos & Gana Dinero',
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila Juegos, Gana Dinero, PS4, Xbox, Nintendo.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',
			properties: {'og:title': 'GameClub - Alquila Videojuegos & Gana Dinero'}
		},
		controller: 'ProfileCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Profile',
					files: ['js/modules/account/profile/controller/profileCtrl.js', '/js/modules/account/profile/style/profile.less', '/js/modules/account/profile/style/profile.responsive.less']
				}]);
			},

			provinces: function(openRest, Const) {
				return openRest("location/findChildrenOf/:code", true).get({code: Const.code.country}, function(data) {
					return data;
				});
			}
		}
	});

});
