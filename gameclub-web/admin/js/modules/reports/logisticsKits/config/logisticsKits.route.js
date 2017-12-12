angular.module("Reports").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'logisticsKits',{
		url: "/logisticsKits",
		templateUrl: "js/modules/reports/logisticsKits/view/logisticsKits.html",
		data: {displayName: 'Logìstica Kits'},
		controller: 'LogisticsKitsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Reports',
					files: ['js/modules/reports/logisticsKits/controller/logisticsKitsCtrl.js']
				}]);
			},
			logisticsKits: function(rest) {
				return rest("report/logisticsKits/find").post();
			},
			totalShippingKidsSold: function(rest) {
				return rest("report/logisticsKits/totalShippingKidsSold").get();
			},
			shippingKidsDelivered: function(rest) {
				return rest("report/logisticsKits/shippingKidsDelivered").get();
			},
			welcomeKitsDelivered: function(rest) {
				return rest("report/logisticsKits/welcomeKitsDelivered").get();
			}
		}

	});
});