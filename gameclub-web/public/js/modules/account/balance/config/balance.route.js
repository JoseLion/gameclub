angular.module('Balance').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'balance', {
		url: '/balance',
		templateUrl: 'js/modules/account/balance/view/balance.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'BalanceCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad, lessLoad) {
				lessLoad.add('css/resources/balance.less');
				return $ocLazyLoad.load([{
					name: 'Balance'
					,files: ['js/modules/account/balance/controller/balanceCtrl.js']
				}]);
			}
		}
	});

});
