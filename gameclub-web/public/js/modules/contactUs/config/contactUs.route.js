angular.module('ContactUs').config(function($stateProvider) {
	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'contactUs', {
		url: '/contact-us',
		templateUrl: 'js/modules/contactUs/view/contactUs.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'Contactanos GameClub',
			description: 'Tienes alguna duda, sugerencia o comentario. Escríbenos a GameClub ¡La única plataforma gamer del Ecuador! Alquila tus Juegos, Gana Dinero, Juega mas pagando menos. Share and Play',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
		},
		controller: 'ContactUsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'ContactUs',
					files: ['js/modules/contactUs/controller/contactUsCtrl.js', 'js/modules/contactUs/style/contactUs.less', 'js/modules/contactUs/style/contactUs.responsive.less']
				}, {
					name: 'LevelapBlog',
					files: ['js/modules/levelapBlog/resources/blog.css', 'js/modules/levelapBlog/resources/mostSeen.js']
				}]);
			},

			blogsPreview: function(openRest) {
				return openRest("levelapBlog/findArticles").post({isMostSeen: true}, function(data) {
					return data;
				});
			}
		}
	});
});