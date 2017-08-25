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

			welcomeKits: function(rest) {
				return rest("welcomeKit/findWelcomeKits").post(function(data) {
					return data;
				});
			},

			loans: function(rest) {
				return rest("loan/findLoans").post(function(data) {
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
			}
		}
	});
});