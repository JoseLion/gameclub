angular.module("Reports").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'registeredUsers',{
		url: "/registeredUsers",
		templateUrl: "js/modules/reports/registeredUsers/view/registeredUsers.html",
		data: {displayName: 'Facturación'},
		controller: 'RegisteredUsersCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Reports',
					files: ['js/modules/reports/registeredUsers/controller/registeredUsersCtrl.js']
				}]);
			},

			registeredUsers: function(rest) {
				return rest("report/registeredUsers/find").post();
			},

			totalUsers: function(rest) {
				return rest("report/registeredUsers/totalUsers").get();
			}
		}

	});
});