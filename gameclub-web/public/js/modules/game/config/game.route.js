angular.module('Game').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'game', {
		url: '/game',
		params: {
			idGame: null
		},
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

			idGame: function($stateParams) {
				return $stateParams.idGame;
			}
		}
	});

});
