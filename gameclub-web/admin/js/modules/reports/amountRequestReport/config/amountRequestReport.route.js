angular.module("Reports").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'amountRequestReport',{
		url: "/amount-request",
		templateUrl: "js/modules/reports/amountRequestReport/view/amountRequestReport.html",
		data: {displayName: 'Retiro de Saldo'},
		controller: 'AmountRequestReportCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Reports',
					files: ['js/modules/reports/amountRequestReport/controller/amountRequestReportCtrl.js']
				}]);
			},

			amountRequests: function(rest) {
				return rest("report/amountRequest/find").post();
			}
		}
	});
});