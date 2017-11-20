angular.module("UserGames").config(function($stateProvider) {
	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'userGames', {
		url: "/user-games",
		templateUrl: "js/modules/userGames/view/userGames.html",
		data: {displayName: 'Juegos por Usuario'},
		controller: "UserGamesCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'UserGames',
					files: ['js/modules/userGames/controller/userGamesCtrl.js']
				}]);
			}
		}
	});
});