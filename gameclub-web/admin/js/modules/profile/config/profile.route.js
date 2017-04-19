angular.module("Profile").config(function($stateProvider) {
	let prefix = 'user.'

	$stateProvider
	.state(prefix + 'viewProfiles', {
		url: "/view-profiles",
		templateUrl: "js/modules/profile/view/viewProfiles.html",
		data: {displayName: 'Ver Perfiles'},
		controller: "ViewProfilesCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Profile',
					files: ['js/modules/profile/controller/viewProfilesCtrl.js']
				}]);
			},

			profiles: function(rest) {
				return rest("profile/findProfiles", true).post(function(data) {
					return data;
				});
			}
		}
	})

	.state(prefix + 'addProfile', {
		url: "/add-profile",
		templateUrl: "js/modules/profile/view/manageProfile.html",
		data: {displayName: 'Agregar Perfil'},
		controller: "ManageProfile",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Profile',
					files: ['js/modules/profile/controller/manageProfileCtrl.js']
				}]);
			},

			navigation: function(rest) {
				return rest("navigation/findAll", true).get(function(data) {
					return data;
				});
			},

			profile: function() {
				return null;
			}
		}
	})

	.state(prefix + 'editProfile', {
		url: "/edit-profile/:name",
		params: {name: null, id: null},
		templateUrl: "js/modules/profile/view/manageProfile.html",
		data: {displayName: 'Agregar Perfil'},
		controller: "ManageProfile",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Profile',
					files: ['js/modules/profile/controller/manageProfileCtrl.js']
				}]);
			},

			navigation: function(rest) {
				return rest("navigation/findAll", true).get(function(data) {
					return data;
				});
			},

			profile: function(rest, $stateParams) {
				return rest("profile/findOne/:id").get({id: $stateParams.id}, function(data) {
					return data;
				});
			}
		}
	});
});