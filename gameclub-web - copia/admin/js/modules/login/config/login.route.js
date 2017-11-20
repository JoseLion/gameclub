angular.module("Login").config(function($stateProvider) {
	$stateProvider
	.state('login', {
		url: "/login",
		templateUrl: "js/modules/login/view/login.html",
		data: {displayName: 'Login', specialClass: 'gray-bg'},
		controller: "LoginCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Login',
					files: ['js/modules/login/controller/loginCtrl.js']
				}]);
			}
		}
	});
});