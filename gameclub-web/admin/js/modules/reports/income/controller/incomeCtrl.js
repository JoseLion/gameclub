angular.module('Reports').controller('IncomeCtrl', function($scope, $rootScope, getDTOptions,rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	$scope.search = {};

	rest("report/incomePage").get(function(data) {
		$scope.incomeList = data.content;
	});

	$scope.find = function() {
		rest("report/findIncome", true).post($scope.search, function(data) {
			$scope.incomeList = data;
		});
	}

	$scope.clean = function() {
		$scope.search = {};
		rest("report/incomePage").get(function(data) {
			$scope.incomeList = data.content;
		});
	}

});