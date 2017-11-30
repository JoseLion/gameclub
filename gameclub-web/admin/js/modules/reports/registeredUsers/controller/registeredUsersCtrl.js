angular.module('Reports').controller('RegisteredUsersCtrl', function($scope, $rootScope, getDTOptions,rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	rest("report/registeredUsersAll").get(function(data) {
		$scope.registeredUsersAll = data.content;
	});

	rest("report/totalUsers").get(function(data) {
		$scope.totalUsers = data;
	});

	$scope.find = function() {
		rest("report/findRegisteredUsers", true).post($scope.search, function(data) {
			$scope.registeredUsersAll = data;
		});
	}
	
	$scope.clean = function() {
		$scope.search = {};
		rest("report/registeredUsersAll").get(function(data) {
			$scope.registeredUsersAll = data.content;
		});
	}
});