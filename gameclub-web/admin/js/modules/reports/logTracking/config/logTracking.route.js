angular.module("Reports").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'logTracking',{
		url: "/logTracking",
		templateUrl: "js/modules/reports/logTracking/view/logTracking.html",
		data: {displayName: 'Log Tracking'},
		controller: 'LogTrackingCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Reports',
					files: ['js/modules/reports/logTracking/controller/logTrackingCtrl.js']
				}]);
			},
			
			logTrackings: function(rest) {
				return rest("report/logTracking/find").post();
			}
		}

	});
});