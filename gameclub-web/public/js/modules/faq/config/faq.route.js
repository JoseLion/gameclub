angular.module('Faq').config(function($stateProvider) {
	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'faqs', {
		url: '/faqs',
		templateUrl: 'js/modules/faq/view/faqs.html',
		data: {displayName: 'Preguntas Frecuentes'},
		metaTags: {
			title: 'Preguntas Frecuentes',
			description: 'Encuentra todas las respuestas a tus preguntas sobre GameClub. ¡La única plataforma gamer del Ecuador! Alquila tus Juegos, Gana Dinero, Juega más pagando menos. Share and Play',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
		},
		controller: 'FaqsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Faq',
					files: ['js/modules/faq/controller/faqsCtrl.js', 'js/modules/faq/style/faqs.less', 'js/modules/faq/style/faqs.responsive.less']
				}]);
			},

			categories: function(openRest, Const) {
				return openRest("catalog/findChildrenOf/:code", true).get({code: Const.code.faq}, function(data) {
					return data;
				});
			}
		}
	});
});
