angular.module('Balance').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'balance', {
		url: '/balance',
		templateUrl: 'js/modules/account/balance/view/balance.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'BalanceCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Balance'
					,files: ['js/modules/account/balance/controller/balanceCtrl.js']
				}]);
			}
			/*transactions: function(rest,$rootScope){
				console.log($rootScope);
				console.log($rootScope.currentUser);
				return rest("transaction/lastFiveTransactions", true).post($rootScope.currentUser,function(data){
					return data;
				});
			}*/
		}
	});

});