angular.module("PublicUser").config(function($stateProvider) {
	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'viewPublicUsers', {
		url: "/view-public-users",
		templateUrl: "js/modules/publicUser/view/viewPublicUsers.html",
		data: {displayName: 'Ver Usuarios del Portal'},
		controller: "ViewPublicUsersCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'PublicUser',
					files: ['js/modules/publicUser/controller/viewPublicUsersCtrl.js']
				}]);
			},

			publicUsers: function(rest) {
				return rest("publicUser/findPublicUsers").post(function(data) {
					return data;
				});
			}
		}
	})

	.state(prefix + 'editPublicUser', {
		url: "/edit-public-user/:id/:fullName",
		params: {fullName: null, id: null},
		templateUrl: "js/modules/publicUser/view/managePublicUser.html",
		data: {displayName: 'Editar Usuario del Portal'},
		controller: "ManagePublicUserCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'PublicUser',
					files: ['js/modules/publicUser/controller/managePublicUserCtrl.js']
				}]);
			},

			publicUser: function(rest, $stateParams) {
				return rest("publicUser/findOne/:id").get({id: $stateParams.id}, function(data) {
					return data;
				});
			},

			locations: function(rest) {
				return rest("location/findChildrenOf/:code", true).get({code: 'EC'}, function(data) {
					return data;
				});
			}
		}
	});
});
