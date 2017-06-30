angular.module('GameClub').config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $locationProvider) {
	$urlRouterProvider.otherwise('/gameclub/home');
	$ocLazyLoadProvider.config({debug: false});
	$locationProvider.html5Mode(true);

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
}).run(function($rootScope, $state, Const, $location, $anchorScroll, $http, urlRestPath) {
	$rootScope.$state = $state;
	$rootScope.Const = Const;

	/*$rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams, options) {
		$http.get(urlRestPath.url + "/api/token").then(function() {}, function(error) {
			$rootScope.currentUser = null;
		});
	});*/

	$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams, options) {
		angular.element('#footer').removeClass('fixed-bottom');
		$location.hash();
		$anchorScroll();
	});
});
