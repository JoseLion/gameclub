angular.module('ShippingKit').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'shippingKit', {
		url: '/account/shipping-kit?error_value',
		templateUrl: 'js/modules/shippingKit/view/shippingKit.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'GameClub - Alquila Videojuegos & Gana Dinero',
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila Juegos, Gana Dinero, PS4, Xbox, Nintendo.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',
			properties: {'og:title': 'GameClub - Alquila Videojuegos & Gana Dinero'}
		},
		controller: 'ShippingKitCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'ShippingKit',
					files: ['js/modules/shippingKit/controller/shippingKitCtrl.js', 'js/modules/shippingKit/style/shippingKit.less', 'js/modules/shippingKit/style/shippingKit.responsive.less']
				}]);
			},

			shippingKitValue: function(rest, Const) {
				return rest('settings/findOneByCode/:code').get({code: Const.settings.shippingKitValue}, function(data) {
					return data;
				});
			},

			addCardError: function($stateParams) {
				return $stateParams.error_value;
			}
		}
	});

});
