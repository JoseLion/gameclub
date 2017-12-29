angular.module('Balance').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'balance', {
		url: '/balance',
		templateUrl: 'js/modules/account/balance/view/balance.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'Balance de Usuario GameClub',
			description: 'Alquilar tu juegos es facil con GameClub! Entra a tu perfil para empezar a ganar dinero.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
		},
		controller: 'BalanceCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Balance',
					files: ['js/modules/account/balance/controller/balanceCtrl.js', 'js/modules/account/balance/style/balance.less', 'js/modules/account/balance/style/balance.responsive.less']
				}]);
			}
		}
	});

});
