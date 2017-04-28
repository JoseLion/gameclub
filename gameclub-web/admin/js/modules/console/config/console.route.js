angular.module("Console").config(function($stateProvider) {
	let prefix = 'portal.'

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
					return data;
				});
			}
		}
	})

	.state(prefix + 'addConsole', {
		url: "/add-console",
		templateUrl: "js/modules/console/view/manageConsole.html",
		data: {displayName: 'Agregar Consola'},
		controller: "ManageConsoleCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Console',
					files: ['js/modules/console/controller/manageConsoleCtrl.js']
				}]);
			},

			consl: function() {
				return null;
			}
		}
	})

	.state(prefix + 'editConsole', {
		url: "/edit-console/:id/:name",
		params: {id: null, name: null},
		templateUrl: "js/modules/console/view/manageConsole.html",
		data: {displayName: 'Editar Consola'},
		controller: "ManageConsoleCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Console',
					files: ['js/modules/console/controller/manageConsoleCtrl.js']
				}]);
			},

			consl: function(rest, $stateParams) {
				return rest("console/findOne/:id").get({id: $stateParams.id}, function(data) {
					return data;
				});
			}
		}
	});
});