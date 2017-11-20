angular.module("Fine").config(function($stateProvider){
	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'viewFine',{
		url: "/view-fine",
		templateUrl: "js/modules/fine/view/viewFine.html",
		data: {displayName: 'Multas'},
		controller: 'ViewFineCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Fine',
					files: ['js/modules/fine/controller/viewFineCtrl.js']
				}]);
			},
			fines: function(rest){
				return rest("fine/findFines", true).post(function(data){
					return data;
				});
			}
		}

	});


});
