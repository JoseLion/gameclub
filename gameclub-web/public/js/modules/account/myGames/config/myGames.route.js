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
					files: ['js/modules/account/myGames/controller/myGamesCtrl.js', 'js/modules/account/myGames/style/myGames.less', 'js/modules/account/myGames/style/myGames.responsive.less', 'js/modules/game/style/game.less']
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
			},

			integrity: function(rest, Const) {
				return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.integrity});
			},

			mostPlayed: function(openRest) {
				return openRest("game/findMostPlayed", true).post();
			},

			shippingKitValue: function(rest, Const) {
				return rest('settings/findOneByCode/:code').get({code: Const.settings.shippingKitValue}, function(data) {
					return data;
				});
			}
		}
	});

});
