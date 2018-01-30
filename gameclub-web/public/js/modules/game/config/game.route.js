angular.module('Game').run(['$rootScope', 'MetaTags', runBlock]).config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'game', {
		url: '/game/:id/:consoleId/:name?error_value',
		params: {id: null, consoleId: null, name: null},
		templateUrl: 'js/modules/game/view/game.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: function(game) {
				return game.name + " GameClub";
			},

			description: function(game) {
				return "Juega " + game.name + " pagando menos en GameClub. Si ya lo tienes, alquila tu juego en GameClub y gana dinero. Share and Play"
			},

			keywords: function(game) {
				return game.name.replace(/\s/g, ", ") + ", GameClub, Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC, Consola, Gamer";
			},

			prerender: {
                statusCode: function(game) {
                    return game != null ? 200 : 302;
                },

                header: function(game, friendlyUrl, $location) {
                    return game != null ? null : 'Location: ' + $location.$$protocol + "://" + $location.$$host + "/gameclub/game/" + game.id + "/" + friendlyUrl(game.name);
                }
            }
		},
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
		},
		
		controller: 'GameCtrl'
	});
});
