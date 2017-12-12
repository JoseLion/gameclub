angular.module('Faq').config(function($stateProvider) {
	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'faqs', {
		url: '/faqs',
		templateUrl: 'js/modules/faq/view/faqs.html',
		data: {displayName: 'Preguntas Frecuentes', description: '', keywords: ''},
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
