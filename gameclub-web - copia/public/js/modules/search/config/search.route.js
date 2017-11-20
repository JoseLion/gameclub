angular.module('Search').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'search', {
		url: '/search/:name/:categoryId/:consoleId/:page/:title',
		params: {name: null, categoryId: null, consoleId: null, page: null, title: null},
		templateUrl: 'js/modules/search/view/search.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
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
