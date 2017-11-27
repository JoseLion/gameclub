angular.module('Reports').controller('LeaseCostsCtrl', function($scope, $rootScope, getDTOptions,rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	rest("report/leaseCostsPage").get(function(data) {
		$scope.leaseCostsList = data.content;
	});

});