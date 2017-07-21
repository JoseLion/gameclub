angular.module("Faq").config(function($stateProvider) {
	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'viewFaqs', {
		url: "/view-faqs",
		templateUrl: "js/modules/faq/view/viewFaqs.html",
		data: {displayName: 'Ver FAQs'},
		controller: "ViewFaqsCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Faq',
					files: ['js/modules/faq/controller/viewFaqsCtrl.js']
				}]);
			},

			faqs: function(rest) {
				return rest("faq/findFaqs", true).post(function(data) {
					return data;
				});
			},

			categories: function(rest) {
				return rest("catalog/findChildrenOf/:code", true).get({code: 'FAQ'}, function(data) {
					return data;
				});
			}
		}
	})

	.state(prefix + 'addFaq', {
		url: "/add-faq",
		templateUrl: "js/modules/faq/view/manageFaq.html",
		data: {displayName: 'Agregar FAQ'},
		controller: "ManageFaqCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Faq',
					files: ['js/modules/faq/controller/manageFaqCtrl.js']
				}]);
			},

			faq: function() {
				return null;
			},

			categories: function(rest) {
				return rest("catalog/findChildrenOf/:code", true).get({code: 'FAQ'}, function(data) {
					return data;
				});
			}
		}
	})

	.state(prefix + 'editFaq', {
		url: "/edit-faq/:id/:question",
		params: {id: null, question: null},
		templateUrl: "js/modules/faq/view/manageFaq.html",
		data: {displayName: 'Editar FAQ'},
		controller: "ManageFaqCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Faq',
					files: ['js/modules/faq/controller/manageFaqCtrl.js']
				}]);
			},

			faq: function($stateParams, rest) {
				return rest("faq/findOne/:id").get({id: $stateParams.id}, function(data) {
					return data;
				});
			},

			categories: function(rest) {
				return rest("catalog/findChildrenOf/:code", true).get({code: 'FAQ'}, function(data) {
					return data;
				});
			}
		}
	});
});