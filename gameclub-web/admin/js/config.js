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
	})

		.state('admin.dashboard', {
			url: "/dashboard",
			templateUrl: "views/dashboard.html",
			controller: "MainCtrl",
			resolve: {
				loadPlugin: function ($ocLazyLoad) {
					return $ocLazyLoad.load([{}]);
				}
			}
		});
}).run(function($rootScope, $state) {
	$rootScope.$state = $state;
});