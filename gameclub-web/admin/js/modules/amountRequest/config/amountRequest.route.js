angular.module("AmountRequest").config(function($stateProvider){

	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'amountRequest',{
		url: "/view-amountRequest",
		templateUrl: "js/modules/amountRequest/view/amountRequest.html",
		data: {displayName: 'Estados de Pagos'},
		controller: 'AmountRequestCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'AmountRequest',
					files: ['js/modules/amountRequest/controller/amountRequestCtrl.js']
				}]);
			},

			amountRequests: function(rest) {
				return rest("amountRequest/findAll", true).post(function(data) {
					return data;
				});
			},

			amountRequestCatalog: function(rest, Const) {
				return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.amountRequest}, function(data) {
					return data;
				});
			},
		}

	});
});