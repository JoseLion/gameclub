angular.module("Reports").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'billing',{
		url: "/billing",
		templateUrl: "js/modules/reports/billing/view/billing.html",
		data: {displayName: 'Facturación'},
		controller: 'BillingCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Reports',
					files: ['js/modules/reports/billing/controller/billingCtrl.js']
				}]);
			},

			billing: function(rest) {
				return rest("report/billing/find").post();
			},

			totalBilling: function(rest) {
				return rest("report/billing/getTotal").get();
			}
		}

	});
});