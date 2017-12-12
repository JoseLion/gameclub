angular.module('Game').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'game', {
		url: '/game/:id/:consoleId/:name?error_value',
		params: {id: null, consoleId: null, name: null},
		templateUrl: 'js/modules/game/view/game.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'GameCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Game',
					files: ['js/modules/game/controller/gameCtrl.js', 'js/modules/game/style/game.less', 'js/modules/game/style/game.responsive.less']
				}]);
			},

			game: function(openRest, $stateParams) {
				return openRest("game/findOne/:id").get({id: $stateParams.id});
			},

			consoleId: function($stateParams) {
				return $stateParams.consoleId;
			},

			availableGames: function($stateParams, openRest) {
				let filter = {
					gameId: $stateParams.id,
					consoleId: $stateParams.consoleId
				};

				return openRest("game/getAvailableGames").post(filter);
			},

			mostPlayed: function(openRest) {
				return openRest("game/findMostPlayed", true).post();
			},

			addCardError: function($stateParams) {
				return $stateParams.error_value;
			}
		}
	});
});
