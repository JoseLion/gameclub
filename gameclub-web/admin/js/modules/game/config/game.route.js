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
				return rest("game/findGames").post(function(data) {
					return data;
				});
			}
		}
	})

	.state(prefix + 'addGame', {
		url: "/add-game",
		templateUrl: "js/modules/game/view/manageGame.html",
		data: {displayName: 'Agregar Juegos'},
		controller: "ManageGameCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Game',
					files: ['js/modules/game/controller/manageGameCtrl.js']
				}]);
			},

			game: function() {
				return null;
			},

			contentRatings: function(rest, Const) {
				return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.contentRatings}, function(data) {
					return data;
				});
			},

			magazines: function(rest, Const) {
				return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.magazines}, function(data) {
					return data;
				});
			}
		}
	})

	.state(prefix + 'editGame', {
		url: "/edit-game/:id/:name",
		params: {id: null, name: null},
		templateUrl: "js/modules/game/view/manageGame.html",
		data: {displayName: 'Agregar Juegos'},
		controller: "ManageGameCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Game',
					files: ['js/modules/game/controller/manageGameCtrl.js']
				}]);
			},

			game: function(rest, $stateParams) {
				return rest("game/findOne/:id").get({id: $stateParams.id}, function(data) {
					return data;
				});
			},

			contentRatings: function(rest, Const) {
				return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.contentRatings}, function(data) {
					return data;
				});
			},

			magazines: function(rest, Const) {
				return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.magazines}, function(data) {
					return data;
				});
			}
		}
	});
});