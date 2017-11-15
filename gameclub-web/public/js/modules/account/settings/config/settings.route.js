angular.module('Settings').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'settings', {
		url: '/settings',
		templateUrl: 'js/modules/account/settings/view/settings.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'SettingsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Settings',
					files: ['js/modules/account/settings/controller/settingsCtrl.js', 'js/modules/account/settings/style/settings.less', 'js/modules/account/settings/style/settings.responsive.less']
				}]);
			},

			reviews: function(rest) {
				return rest("review/getReviewsOfCurrentUser/:page").get({page: 0});
			}
		}
	});

});
