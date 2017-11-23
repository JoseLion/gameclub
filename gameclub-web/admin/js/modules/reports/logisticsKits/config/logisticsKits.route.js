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
			}
		}

	});
});