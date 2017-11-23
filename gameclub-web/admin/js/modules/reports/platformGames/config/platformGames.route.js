angular.module("Reports").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'platformGames',{
		url: "/platformGames",
		templateUrl: "js/modules/reports/platformGames/view/platformGames.html",
		data: {displayName: 'Facturación'},
		controller: 'PlatformGamesCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Reports',
					files: ['js/modules/reports/platformGames/controller/platformGamesCtrl.js']
				}]);
			}
		}

	});
});