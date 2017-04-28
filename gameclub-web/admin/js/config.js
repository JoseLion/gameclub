angular.module("Gameclub").config(function config($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, KeepaliveProvider) {
	$urlRouterProvider.otherwise("/admin/dashboard");

	$ocLazyLoadProvider.config({
		debug: false
	});

	$stateProvider
	.state('admin', {
		abstract: true,
		url: "/admin",
		templateUrl: "views/common/content.html",
		data: {displayName: "Administración"}
	})

		.state('admin.dashboard', {
			url: "/dashboard",
			templateUrl: "views/dashboard.html",
			data: {displayName: 'Dashboard'},
			resolve: {
				loadPlugin: function ($ocLazyLoad) {
					return $ocLazyLoad.load([{}]);
				}
			}
		})

	.state('portal', {
		abstract: true,
		url: "/portal",
		templateUrl: "views/common/content.html",
		data: {displayName: "Portal"}
	});
}).run(function($rootScope, $state, Const, rest, $location, $anchorScroll, authenticate) {
	$rootScope.$state = $state;
	$rootScope.Const = Const;

	$rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams, options) {
		if (toState.name != Const.loginState) {
			rest("token").get(function(data) {
				if (data.token) {
					if ($rootScope.redirect != null && fromState.name == Const.loginState) {
						event.preventDefault();
						$state.go($rootScope.redirect);
					} else {
						return;
					}
				} else {
					goToLogin(event, toState);
				}
			}, function(error) {
				goToLogin(event, toState);
			});
		}

		if (fromState.name == "") {
			authenticate().then(function(response) {
				if (response.data == Const.ok) {
					if (toState.name == Const.loginState) {
						if ($rootScope.redirect != null && $rootScope.redirect != Const.loginState) {
							$state.go($rootScope.redirect);
						} else {
							$state.go(Const.mainState);
						}
					} else {
						return;
					}
				} else {
					event.preventDefault();
					$state.go(Const.loginState);
				}
			}, function(error) {
				if ((error.status == 401 || error.status < 0) && toState.name != Const.loginState) {
					if (toState.name != Const.loginState) {
						$rootScope.redirect = toState.name;
					}
					
					goToLogin(event, toState);
				}
			});
		}
	});

	$rootScope.$on("$stateChangeSuccess", function(event, toState, toParams, fromState, fromParams, options) {
		if (toState.name != Const.loginState) {
			$rootScope.redirect = toState.name;
		}
		
		$location.hash();
		$anchorScroll();
	});

	function goToLogin(event, toState) {
		event.preventDefault();

		if (toState.name != Const.loginState) {
			$state.go(Const.loginState);
		}
	}
});