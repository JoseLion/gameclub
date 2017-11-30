angular.module("Reports").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'leaseCosts',{
		url: "/leaseCosts",
		templateUrl: "js/modules/reports/leaseCosts/view/leaseCosts.html",
		data: {displayName: 'Costos de Arriendo'},
		controller: 'LeaseCostsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Reports',
					files: ['js/modules/reports/leaseCosts/controller/leaseCostsCtrl.js']
				}]);
			},

			leaseCosts: function(rest) {
				return rest("report/leaseCost/find").post();
			}
		}

	});
});