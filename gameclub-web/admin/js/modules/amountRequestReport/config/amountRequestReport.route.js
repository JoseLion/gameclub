angular.module("AmountRequestReport").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'amountRequestReport',{
		url: "/amount-request",
		templateUrl: "js/modules/amountRequestReport/view/amountRequestReport.html",
		data: {displayName: 'Retiro de Saldo'},
		controller: 'AmountRequestReportCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'AmountRequestReport',
					files: ['js/modules/amountRequestReport/controller/amountRequestReportCtrl.js']
				}]);
			},

			amountRequests: function(rest) {
				return rest("report/amountRequest/find").post();
			}
		}

	});
});