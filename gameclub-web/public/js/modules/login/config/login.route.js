angular.module('Login').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'login', {
		url: '/login?token',
		params: {redirect: null, token: null},
		templateUrl: 'js/modules/login/view/login.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'GameClub - Alquila Videojuegos & Gana Dinero',
			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila Juegos, Gana Dinero, PS4, Xbox, Nintendo.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',
			properties: {'og:title': 'GameClub - Alquila Videojuegos & Gana Dinero'}
		},
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
