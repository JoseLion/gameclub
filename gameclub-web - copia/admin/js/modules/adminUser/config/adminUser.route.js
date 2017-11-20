angular.module("AdminUser").config(function($stateProvider) {
	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'viewAdminUsers', {
		url: "/view-admin-users",
		templateUrl: "js/modules/adminUser/view/viewAdminUsers.html",
		data: {displayName: 'Ver Usuarios del Administrador'},
		controller: "ViewAdminUsersCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'AdminUser',
					files: ['js/modules/adminUser/controller/viewAdminUsersCtrl.js']
				}]);
			},

			adminUsers: function(rest) {
				return rest("adminUser/findAdminUsers", true).post(function(data) {
					return data;
				});
			},

			profiles: function(rest) {
				return rest("profile/findProfiles", true).post(function(data) {
					return data;
				});
			}
		}
	})

	.state(prefix + 'addAdminUser', {
		url: "/add-admin-user",
		templateUrl: "js/modules/adminUser/view/manageAdminUser.html",
		data: {displayName: 'Agregar Usuario del Administrador'},
		controller: "ManageAdminUserCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'AdminUser',
					files: ['js/modules/adminUser/controller/manageAdminUserCtrl.js']
				}]);
			},

			profiles: function(rest) {
				return rest("profile/findProfiles", true).post(function(data) {
					return data;
				});
			},

			adminUser: function() {
				return null;
			}
		}
	})

	.state(prefix + 'editAdminUser', {
		url: "/edit-admin-user/:id/:fullName",
		params: {fullName: null, id: null},
		templateUrl: "js/modules/adminUser/view/manageAdminUser.html",
		data: {displayName: 'Agregar Usuario del Administrador'},
		controller: "ManageAdminUserCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'AdminUser',
					files: ['js/modules/adminUser/controller/manageAdminUserCtrl.js']
				}]);
			},

			profiles: function(rest) {
				return rest("profile/findProfiles", true).post(function(data) {
					return data;
				});
			},

			adminUser: function(rest, $stateParams) {
				return rest("adminUser/findOne/:id").get({id: $stateParams.id}, function(data) {
					return data;
				});
			}
		}
	});
});