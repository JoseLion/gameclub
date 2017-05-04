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
					files: ['js/modules/account/profile/controller/profileCtrl.js']
				}]);
			},

			provinces: function(openRest) {
				return openRest("location/findChildrenOf/:code", true).get({code: 'ECAZ'}, function(data) {
					return data;
				});
			}
		}
	});

});
