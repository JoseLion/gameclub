angular.module('Referred').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'referred', {
		url: '/referred',
		templateUrl: 'js/modules/account/referred/view/referred.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'ReferredCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Referred'
					,files: ['js/modules/account/referred/controller/referredCtrl.js']
				}]);
			}
		}
	});

});
