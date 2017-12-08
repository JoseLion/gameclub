angular.module('Login').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'login', {
		url: '/login?token',
		params: {redirect: null, token: null},
		templateUrl: 'js/modules/login/view/login.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'LoginCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Login',
					files: ['js/modules/login/controller/loginCtrl.js', 'js/modules/login/style/login.less', 'js/modules/login/style/login.responsive.less']
				}]);
			},

			redirect: function($stateParams) {
				return $stateParams.redirect;
			},

			token: function($stateParams) {
				return $stateParams.token;
			}
		}
	})

	.state(prefix + 'validate', {
		url: '/validate?token&id',
		params: {token: null, id: null},
		templateUrl: 'js/modules/login/view/validate.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'ValidateCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Login',
					files: ['js/modules/login/controller/validateCtrl.js', 'js/modules/login/style/validate.less', 'js/modules/login/style/validate.responsive.less']
				}]);
			},

			token: function($stateParams) {
				return $stateParams.token;
			},

			id: function($stateParams) {
				return $stateParams.id;
			}
		}
	});

});
