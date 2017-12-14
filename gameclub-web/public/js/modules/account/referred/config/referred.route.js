angular.module('Referred').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'referred', {
		url: '/referred',
		templateUrl: 'js/modules/account/referred/view/referred.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'GameClub - Alquila Videojuegos & Gana Dinero',
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila Juegos, Gana Dinero, PS4, Xbox, Nintendo.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',
			properties: {'og:title': 'GameClub - Alquila Videojuegos & Gana Dinero'}
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
