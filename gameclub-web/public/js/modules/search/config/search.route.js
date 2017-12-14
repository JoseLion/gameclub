angular.module('Search').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'search', {
		url: '/search?name&categoryId&consoleId&page',
		params: {name: null, categoryId: null, consoleId: null, page: null},
		templateUrl: 'js/modules/search/view/search.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'GameClub - Alquila Videojuegos & Gana Dinero',
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila Juegos, Gana Dinero, PS4, Xbox, Nintendo.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',
			properties: {'og:title': 'GameClub - Alquila Videojuegos & Gana Dinero'}
		},
		controller: 'SearchCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Search',
					files: ['js/modules/search/controller/searchCtrl.js', 'js/modules/search/style/search.less', 'js/modules/search/style/search.responsive.less']
				}]);
			},

			games: function(openRest, $stateParams) {
				let search = {
					name: $stateParams.name != null ? $stateParams.name : "",
					categoryId: $stateParams.categoryId,
					consoleId: $stateParams.consoleId,
					page: $stateParams.page != null ? $stateParams.page : 0
				};

				return openRest("game/findGames").post(search, function(data) {
					return data;
				});
			},

			search: function($stateParams) {
				return {
					name: $stateParams.name != null ? $stateParams.name : "",
					categoryId: $stateParams.categoryId,
					consoleId: $stateParams.consoleId,
					$stateParams: $stateParams.page != null ? $stateParams.page : 0
				};
			}
		}
	});

});
