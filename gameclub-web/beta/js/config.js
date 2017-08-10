angular.module('GameClub').config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $locationProvider, UIRouterMetatagsProvider) {
	$urlRouterProvider.otherwise('/gameclub/home');
	$ocLazyLoadProvider.config({debug: false});
	$locationProvider.html5Mode(true);

	UIRouterMetatagsProvider.setTitlePrefix('Game Club | ')
							.setDefaultTitle('Game Club')
							.setDefaultDescription('Intercambia juegos con la comunidad gamer más grande del Ecuador')
							.setDefaultKeywords('gamers,juegos,intercambio,ecuador,consolas')
							.setStaticProperties({
								'fb:app_id': '1819533121697152',
								'og:site_name': 'www.gameclub.com.ec'
							})
							.setOGURL(true);


	$stateProvider
		.state('gameclub', {
			abstract: true,
			url: '/gameclub',
			templateUrl: 'views/content.html'
		})
			.state('gameclub.account', {
				url: '/account',
				templateUrl: 'views/account.html',
				controller: "AccountCtrl",
				resolve: {
					loadPlugin: function($ocLazyLoad) {
						return $ocLazyLoad.load([{
							name: 'GameClub',
							files: ['js/modules/account/accountCtrl.js']
						}]);
					},

					token: function(rest) {
						return rest("token").get(function(data) {
							return data;
						}, function(error) {
							return error;
						});
					}
				}
			})
		.state('levelapBlog', {
			abstract: true,
			url: '/gameclub',
			templateUrl: 'views/content.html'
		});
}).run(function($rootScope, $state, Const, $location, $anchorScroll, $http, urlRestPath, MetaTags) {
	$rootScope.$state = $state;
	$rootScope.Const = Const;
	$rootScope.MetaTags = MetaTags;

	$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams, options) {
		angular.element('#footer').removeClass('fixed-bottom');
		$location.hash();
		$anchorScroll();
	});
});
