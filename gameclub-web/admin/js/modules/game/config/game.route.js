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
			},

			consoles: function(rest) {
				return rest("console/findAll", true).get(function(data) {
					return data;
				});
			},

			categories: function(rest) {
				return rest("category/findAll", true).get(function(data) {
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
				}, {
					files: ['js/plugins/rzslider/rzslider.min.css']
				}, {
					name: 'rzModule',
					files: ['js/plugins/rzslider/rzslider.min.js']
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
			},

			consoles: function(rest) {
				return rest("console/findAll", true).get(function(data) {
					return data;
				});
			},

			categories: function(rest) {
				return rest("category/findAll", true).get(function(data) {
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
				}, {
					files: ['js/plugins/rzslider/rzslider.min.css']
				}, {
					name: 'rzModule',
					files: ['js/plugins/rzslider/rzslider.min.js']
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
			},

			consoles: function(rest) {
				return rest("console/findAll", true).get(function(data) {
					return data;
				});
			},

			categories: function(rest) {
				return rest("category/findAll", true).get(function(data) {
					return data;
				});
			}
		}
	});
});