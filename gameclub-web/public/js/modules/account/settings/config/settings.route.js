angular.module('Settings').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'settings', {
		url: '/settings?error_value',
		templateUrl: 'js/modules/account/settings/view/settings.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'SettingsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad, lessLoad) {
				lessLoad.add('css/resources/settings.less');
				return $ocLazyLoad.load([{
					name: 'Settings',
					files: ['js/modules/account/settings/controller/settingsCtrl.js']
				}]);
			},

			reviews: function(rest) {
				return rest("review/getReviewsOfCurrentUser/:page").get({page: 0});
			},

			addCardError: function($stateParams) {
				return $stateParams.error_value;
			},

			cardsList: function(rest) {
				return rest("paymentez/listCards", true).get();
			}
		}
	});

});
