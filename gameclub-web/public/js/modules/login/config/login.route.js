angular.module('Login').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'login', {
		url: '/login',
		templateUrl: 'js/modules/login/view/login.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'LoginCtrl',
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
