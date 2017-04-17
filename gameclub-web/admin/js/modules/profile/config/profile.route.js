angular.module("Profile").config(function($stateProvider) {
	let prefix = 'user.'

	$stateProvider
	.state(prefix + 'viewProfiles', {
		url: "/viewProfiles",
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
		url: "/addProfile",
		templateUrl: "js/modules/profile/view/manageProfile.html",
		data: {displayName: 'Agregar Perfil'},
		controller: "AddProfileCtrl",
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
			}
		}
	});
});