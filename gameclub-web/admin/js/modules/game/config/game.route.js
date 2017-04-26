angular.module("Game").config(function($stateProvider) {
	let prefix = 'portal.'

	$stateProvider
	.state(prefix + 'viewGames', {
		url: "/view-games",
		templateUrl: "js/modules/game/view/viewGames.html",
		data: {displayName: 'Ver Juegos'},
		controller: "ViewGamesCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Game',
					files: ['js/modules/game/controller/viewGamesCtrl.js']
				}]);
			},

			games: function(rest) {
				return rest("game/findGames").get(function(data) {
					return data;
				});
			}
		}
	});
});