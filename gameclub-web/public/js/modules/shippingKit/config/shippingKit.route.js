angular.module('ShippingKit').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'shippingKit', {
		url: '/account/shipping-kit',
		templateUrl: 'js/modules/shippingKit/view/shippingKit.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'ShippingKitCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'ShippingKit',
					files: ['js/modules/shippingKit/controller/shippingKitCtrl.js', 'js/modules/shippingKit/style/shippingKit.less']
				}]);
			},

			shippingKitValue: function(rest, Const) {
				return rest('settings/findOneByCode/:code').get({code: Const.settings.shippingKitValue}, function(data) {
					return data;
				});
			}
		}
	});

});
