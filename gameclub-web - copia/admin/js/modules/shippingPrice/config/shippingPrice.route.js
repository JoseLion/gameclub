angular.module("ShippingPrice").config(function($stateProvider) {
	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'shippingPrices', {
		url: "/shipping-prices",
		templateUrl: "js/modules/shippingPrice/view/shippingPrice.html",
		data: {displayName: 'Precios de Shipping'},
		controller: "ShippingPriceCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'ShippingPrice',
					files: ['js/modules/shippingPrice/controller/shippingPriceCtrl.js']
				}]);
			},

			shipping: function(rest) {
				return rest("shippingPrice/findShippingPrices").post();
			}
		}
	});
});