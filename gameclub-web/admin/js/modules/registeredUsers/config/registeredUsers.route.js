angular.module("RegisteredUsers").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'registeredUsers',{
		url: "/registered-users",
		templateUrl: "js/modules/registeredUsers/view/registeredUsers.html",
		data: {displayName: 'Usuarios Registrados'},
		controller: 'RegisteredUsersCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'RegisteredUsers',
					files: ['js/modules/registeredUsers/controller/registeredUsersCtrl.js']
				}]);
			},

			registeredUsersAll: function(rest) {
				return rest("registeredUsers/registeredUsersAll", true).post(function(data) {
					return data;
				});
			}
		}

	});
});