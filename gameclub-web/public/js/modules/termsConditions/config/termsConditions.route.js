angular.module('TermsConditions').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'termsConditions', {
		url: '/termsConditions',
		params: {
			user: null,
			isFacebook: null
		},
		templateUrl: 'js/modules/termsConditions/view/termsConditions.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'TermsConditionsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'TermsConditions',
					files: ['js/modules/termsConditions/controller/termsConditionsCtrl.js']
				}]);
			},
			user: function($stateParams) {
				return $stateParams.user;
			},
			isFacebook: function($stateParams) {
				return $stateParams.isFacebook;
			}
		}
	});

});
