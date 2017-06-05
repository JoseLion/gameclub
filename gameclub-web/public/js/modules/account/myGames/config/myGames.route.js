angular.module('MyGames').config(function($stateProvider) {

	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'myGames', {
		url: '/my-games',
		params: {game: null, consoleSelected: null},
		templateUrl: 'js/modules/account/myGames/view/myGames.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'MyGamesCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'MyGames',
					files: ['js/modules/account/myGames/controller/myGamesCtrl.js']
				}]);
			},

			gamesList: function(rest) {
				return rest("publicUser/getGamesList").post(function(data) {
					return data;
				});
			},

			game: function($stateParams) {
				return $stateParams.game;
			},

			consoleSelected: function($stateParams) {
				return $stateParams.consoleSelected;
			}
		}
	});

});
