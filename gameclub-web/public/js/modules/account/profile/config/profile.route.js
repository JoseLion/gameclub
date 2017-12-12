angular.module('Profile').config(function($stateProvider) {

	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'profile', {
		url: '/profile',
		templateUrl: 'js/modules/account/profile/view/profile.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'ProfileCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Profile',
					files: ['js/modules/account/profile/controller/profileCtrl.js', '/js/modules/account/profile/style/profile.less', '/js/modules/account/profile/style/profile.responsive.less']
				}]);
			},

			provinces: function(openRest, Const) {
				return openRest("location/findChildrenOf/:code", true).get({code: Const.code.country}, function(data) {
					return data;
				});
			}
		}
	});

});
