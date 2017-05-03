angular.module('GameClub').config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

	$urlRouterProvider.otherwise('/gameclub/home');
	$ocLazyLoadProvider.config({debug: false});

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
						});
					}
				}
			});

}).run(function($rootScope, $state, Const, $location, $anchorScroll) {
	$rootScope.$state = $state;
	$rootScope.Const = Const;

	$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams, options) {
		$location.hash();
		$anchorScroll();
	});
});
