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
			title: 'Términos y Condiciones GameClub',
			description: 'Alquilar tu juegos es facil con GameClub! Entra a tu perfil para empezar a ganar dinero.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
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
