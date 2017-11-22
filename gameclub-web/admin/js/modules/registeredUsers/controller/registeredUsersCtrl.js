angular.module('RegisteredUsers').controller('RegisteredUsersCtrl', function($scope, $rootScope, getDTOptions,rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	rest("registeredUsers/registeredUsersAll").get(function(data) {
		$scope.registeredUsersAll = data.content;
	});

	rest("registeredUsers/totalUsers").get(function(data) {
		$scope.totalUsers = data;
	});
});