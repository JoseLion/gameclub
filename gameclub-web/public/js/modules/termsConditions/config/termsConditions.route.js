angular.module('TermsConditions').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'termsConditions', {
		url: '/termsConditions',
		params: {
			user: null,
			isFacebook: null
		},
		templateUrl: 'js/modules/termsConditions/view/termsConditions.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'GameClub - Alquila Videojuegos & Gana Dinero',
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila Juegos, Gana Dinero, PS4, Xbox, Nintendo.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',
			properties: {'og:title': 'GameClub - Alquila Videojuegos & Gana Dinero'}
		},
		controller: 'TermsConditionsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'TermsConditions',
					files: ['js/modules/termsConditions/controller/termsConditionsCtrl.js', 'js/modules/termsConditions/style/termsConditions.less']
				}]);
			},
			user: function($stateParams) {
				return $stateParams.user;
			},
			isFacebook: function($stateParams) {
				return $stateParams.isFacebook;
			}
		}
	});

});
