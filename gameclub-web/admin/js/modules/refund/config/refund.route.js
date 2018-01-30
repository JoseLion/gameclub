angular.module("Refund").config(function($stateProvider){
	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'viewRefund',{
		url: "/view-refund",
		templateUrl: "js/modules/refund/view/viewRefund.html",
		data: {displayName: 'Reembolsos'},
		controller: 'ViewRefundCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Refund',
					files: ['js/modules/refund/controller/viewRefundCtrl.js']
				}]);
			},
			refunds: function(rest){
				return rest("refund/findRefunds", true).post(function(data){
					return data;
				});
			}
		}

	});


});
