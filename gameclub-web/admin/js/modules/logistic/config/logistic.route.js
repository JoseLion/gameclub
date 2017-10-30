angular.module("Logistic").config(function($stateProvider) {
	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'viewLogistics', {
		url: "/view-logistics",
		templateUrl: "js/modules/logistic/view/viewLogistics.html",
		data: {displayName: 'Logística'},
		controller: "ViewLogisticsCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Logistic',
					files: ['js/modules/logistic/controller/viewLogisticsCtrl.js']
				}]);
			},

			loans: function(rest) {
				return rest("loan/findLoans").post(function(data) {
					return data;
				});
			},

			restores: function(rest) {
				return rest("restore/findRestores").post(function(data) {
					return data;
				});
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

			provinces: function(rest, Const) {
				return rest("location/findChildrenOf/:code", true).get({code: Const.code.locationEC}, function(data) {
					return data;
				});
			},

			shippingKits: function(rest) {
				return rest("welcomeKit/findShippingKits").post(function(data) {
					return data;
				});
			}
		}
	});
});
