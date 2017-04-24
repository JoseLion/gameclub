angular.module("Console").config(function($stateProvider) {
	let prefix = 'gameclub.'

	$stateProvider
	.state(prefix + 'viewConsoles', {
		url: "/view-consoles",
		templateUrl: "js/modules/console/view/viewConsoles.html",
		data: {displayName: 'Ver Consolas'},
		controller: "ViewConsolesCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Console',
					files: ['js/modules/console/controller/viewConsolesCtrl.js']
				}]);
			},

			consoles: function(rest) {
				return rest("console/findAll", true).get(function(data) {
					return data,
				});
			}
		}
	});
});