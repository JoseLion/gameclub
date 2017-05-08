angular.module('Search').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'search', {
		url: '/search/:title',
		params: {search: null, title: null},
		templateUrl: 'js/modules/search/view/search.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'SearchCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Search',
					files: ['js/modules/search/controller/searchCtrl.js']
				}]);
			},

			games: function(openRest, $stateParams) {
				return openRest("game/findGames").post($stateParams.search, function(data) {
					return data;
				});
			},

			search: function($stateParams) {
				return $stateParams.search;
			}
		}
	});

});
