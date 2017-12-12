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
				abstract: true,
				templateUrl: 'views/account.html',
				controller: "AccountCtrl",
				resolve: {
					loadPlugin: function($ocLazyLoad) {
						return $ocLazyLoad.load([{
							name: 'GameClub',
							files: ['js/modules/account/accountCtrl.js', 'css/account.less', 'css/accountLinks.less', 'css/account.responsive.less', 'css/accountLinks.responsive.less']
						}]);
					},

					token: function(rest) {
						return rest("token").get(function(data) {
							return data;
						}, function(error) {
							return error;
						});
					},

					gamesSummary: function(rest) {
						return rest("publicUser/getGamesSummary").get();
					}
				}
			})
		.state('levelapBlog', {
			abstract: true,
			url: '/gameclub',
			templateUrl: 'views/content.html'
		});

}).run(function($rootScope, $state, Const, $location, $anchorScroll, $http, urlRestPath, rest) {
	$rootScope.$state = $state;
	$rootScope.Const = Const;
	$rootScope.RestUrl = urlRestPath.url;
	$rootScope.$archiveUrl = urlRestPath.url + "/open/archive/download/";

	$rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams, options) {
		$http.get(urlRestPath.url + "/api/token").then(function() {
			return;
		}, function(error) {
			$rootScope.currentUser = null;
		});
		if($rootScope.currentUser != null) {
			rest("publicUser/updateLoggedInformation").get(function(data) {
				$rootScope.currentUser.shownBalance = data.shownBalance;
				$rootScope.currentUser.unreadMessages = data.unreadMessages;
			});
		}
		angular.element('#myNavbar').collapse('hide');
	});

	$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams, options) {
		angular.element('#footer').removeClass('fixed-bottom');
		$location.hash();
		$anchorScroll();
	});
});
