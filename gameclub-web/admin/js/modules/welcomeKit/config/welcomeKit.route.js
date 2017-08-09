angular.module("WelcomeKit").config(function($stateProvider) {
	let prefix = 'portal.'

	$stateProvider
	.state(prefix + 'viewWelcomeKits', {
		url: "/view-welcome-kits",
		templateUrl: "js/modules/welcomeKit/view/viewWelcomeKits.html",
		data: {displayName: 'Ver Welcome Kits'},
		controller: "ViewWelcomeKitsCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'WelcomeKit',
					files: ['js/modules/welcomeKit/controller/viewWelcomeKitsCtrl.js']
				}]);
			},

			welcomeKits: function(rest) {
				return rest("welcomeKit/findWelcomeKits").post(function(data) {
					return data;
				});
			},

			shippingCatalog: function(rest, Const) {
				return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.shippingCatalog}, function(data) {
					return data;
				});
			},

			locations: function(rest, Const) {
				return rest("location/findChildrenOf/:code", true).get({code: Const.code.locationEC}, function(data) {
					return data;
				});
			}
		}
	});
});