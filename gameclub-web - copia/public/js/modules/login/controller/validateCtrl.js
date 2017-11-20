angular.module('Login').controller('ValidateCtrl', function($scope, $rootScope, token, id, $state, Const, openRest, notif) {
	$scope.isLoading = true;

	if (token == null || id == null) {
		$state.go(Const.mainState);
	} else {
		openRest("publicUser/verifyAccount/:token/:id").get({token: token, id: id}, function(data) {
			$rootScope.currentUser = data;
			$scope.isLoading = false;
		}, function(error) {
			$scope.isLoading = false;
			notif.danger(error.data != null ? error.data.message : error.message);
			$state.go(Const.mainState);
		});
	}

    angular.element('#footer').addClass('fixed-bottom');
});
