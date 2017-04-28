angular.module('Games').config(function($stateProvider) {

	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'games', {
		url: '/games',
		templateUrl: 'js/modules/account/games/view/games.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'GamesCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Games',
					files: ['js/modules/account/games/controller/gamesCtrl.js']
				}]);
			}
		}
	});

});
