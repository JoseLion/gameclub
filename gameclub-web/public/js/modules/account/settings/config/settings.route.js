angular.module('Settings').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'settings', {
		url: '/settings',
		templateUrl: 'js/modules/account/settings/view/settings.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'Cuenta de Usuario GameClub',
			description: 'Alquilar tu juegos es facil con GameClub! Entra a tu perfil para empezar a ganar dinero.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
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

			cardsList: function(rest) {
				return rest("paymentez/listCards", true).get();
			}
		}
	});

});
