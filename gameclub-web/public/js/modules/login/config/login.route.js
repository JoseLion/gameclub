angular.module('Login').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'login', {
		url: '/login?token',
		params: {redirect: null, token: null},
		templateUrl: 'js/modules/login/view/login.html',
		metaTags: {
			title: 'Inicio de Sesión GameClub',
			description: 'No eres parte de GameClub, que esperas! Registrate con GameClub ¡La única plataforma gamer del Ecuador! Alquila tus Juegos, Gana Dinero, Juega mas pagando menos. Share and Play',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
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
		metaTags: {
			title: 'Validación de cuenta GameClub',
			description: 'No eres parte de GameClub, que esperas! Registrate con GameClub ¡La única plataforma gamer del Ecuador! Alquila tus Juegos, Gana Dinero, Juega mas pagando menos. Share and Play',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
		},
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
