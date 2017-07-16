angular.module('Game').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'game', {
		url: '/game/:id/:consoleId/:name',
		params: {id: null, consoleId: null, name: null},
		templateUrl: 'js/modules/game/view/game.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'GameCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Game',
					files: ['js/modules/game/controller/gameCtrl.js']
				}]);
			},

			game: function(openRest, $stateParams) {
				return openRest("game/findOne/:id").get({id: $stateParams.id}, function(data) {
					return data;
				});
			},

			consoleId: function($stateParams) {
				return $stateParams.consoleId;
			},

			availableGames: function($stateParams, openRest) {
				let filter = {
					gameId: $stateParams.id,
					consoleId: $stateParams.consoleId
				};

				return openRest("game/getAvailableGames").post(filter, function(data) {
					return data;
				});
			}
		}
	});

});
