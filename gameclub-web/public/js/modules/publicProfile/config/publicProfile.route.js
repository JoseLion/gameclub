angular.module('PublicProfile').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider

	.state(prefix + 'publicProfile', {
		url: '/publicProfile',	
		templateUrl: 'js/modules/publicProfile/view/publicProfile.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'PublicProfileCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'PublicProfile',
					files: ['js/modules/publicProfile/controller/publicProfileCtrl.js']
				}]);
			}
		}
	});

});
