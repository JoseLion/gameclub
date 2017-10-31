angular.module('AmountRequest').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'amountRequest', {
		url: '/amount-request',
		params: {},
		templateUrl: 'js/modules/amountRequest/view/amountRequest.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'AmountRequestCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad, lessLoad) {
				lessLoad.add('css/resources/amountRequest.less');
				return $ocLazyLoad.load([{
					name: 'AmountRequest',
					files: ['js/modules/amountRequest/controller/amountRequestCtrl.js']
				}]);
			},

			provinces: function(openRest, Const) {
				return openRest("location/findChildrenOf/:code", true).get({code: Const.code.country});
			}
		}
	});
});