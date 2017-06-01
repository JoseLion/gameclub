angular.module("Avatar").config(function($stateProvider) {
	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'viewAvatars', {
		url: "/view-avatars",
		templateUrl: "js/modules/avatar/view/viewAvatars.html",
		data: {displayName: 'Ver Avatares'},
		controller: "ViewAvatarsCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Avatar',
					files: ['js/modules/avatar/controller/viewAvatarsCtrl.js']
				}]);
			},

			avatars: function(rest) {
				return rest("avatar/findAll", true).get(function(data) {
					return data;
				});
			}
		}
	});

});
