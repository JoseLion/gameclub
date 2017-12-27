angular.module('Referred').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'referred', {
		url: '/referred',
		templateUrl: 'js/modules/account/referred/view/referred.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'Referidos GameClub',
			description: 'Alquilar tu juegos es facil con GameClub! Entra a tu perfil para empezar a ganar dinero.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
		},
		controller: 'ReferredCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Referred',
					files: ['js/modules/account/referred/controller/referredCtrl.js', 'js/modules/account/referred/style/referred.less', 'js/modules/account/referred/style/referred.responsive.less']
				}]);
			}
		}
	});
});
