angular.module('AmountRequest').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'amountRequest', {
		url: '/amount-request',
		params: {},
		templateUrl: 'js/modules/amountRequest/view/amountRequest.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'Retiro de Balance GameClub',
			description: 'Alquilar tu juegos es facil con GameClub! Entra a tu perfil para empezar a ganar dinero.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
		},
		controller: 'AmountRequestCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'AmountRequest',
					files: ['js/modules/amountRequest/controller/amountRequestCtrl.js', 'js/modules/amountRequest/style/amountRequest.less', 'js/modules/amountRequest/style/amountRequest.responsive.less']
				}]);
			},

			provinces: function(openRest, Const) {
				return openRest("location/findChildrenOf/:code", true).get({code: Const.code.country});
			}
		}
	});
});