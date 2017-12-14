angular.module('Settings').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'settings', {
		url: '/settings?error_value',
		templateUrl: 'js/modules/account/settings/view/settings.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'GameClub - Alquila Videojuegos & Gana Dinero',
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila Juegos, Gana Dinero, PS4, Xbox, Nintendo.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',
			properties: {'og:title': 'GameClub - Alquila Videojuegos & Gana Dinero'}
		},
		controller: 'SettingsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Settings',
					files: ['js/modules/account/settings/controller/settingsCtrl.js', 'js/modules/account/settings/style/settings.less', 'js/modules/account/settings/style/settings.responsive.less']
				}]);
			},

			reviews: function(rest) {
				return rest("review/getReviewsOfCurrentUser/:page").get({page: 0});
			},

			addCardError: function($stateParams) {
				return $stateParams.error_value;
			},

			cardsList: function(rest) {
				return rest("paymentez/listCards", true).get();
			}
		}
	});

});
