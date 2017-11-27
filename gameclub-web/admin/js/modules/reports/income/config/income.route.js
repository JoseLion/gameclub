angular.module("Reports").config(function($stateProvider){

	let prefix = 'reports.'

	$stateProvider
	.state(prefix + 'income',{
		url: "/income",
		templateUrl: "js/modules/reports/income/view/income.html",
		data: {displayName: 'Ingresos'},
		controller: 'IncomeCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Reports',
					files: ['js/modules/reports/income/controller/incomeCtrl.js']
				}]);
			}
		}

	});
});