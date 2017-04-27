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
				controller: [
					'$state', function($state) {
						if($state.current.name == 'gameclub.account') {
							$state.go('gameclub.account.profile')
						}
					}
				]
			});

}).run(function($rootScope, $state, Const, $location, $anchorScroll) {
	$rootScope.$state = $state;
	$rootScope.Const = Const;

	$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams, options) {
		$location.hash();
		$anchorScroll();
	});
});
