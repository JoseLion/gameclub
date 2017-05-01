angular.module('Login').controller('LoginCtrl', function($scope, $rootScope, sweet, openRest, $state, Const, authenticate, rest) {
	$scope.user = {};
	$scope.credentials = {};
	$scope.passRegex = '^(?=.*[0-9])(?=.*[A-Z])([0-9-a-zA-Z]+)$';

	$scope.signIn = function() {
		if ($scope.user.terms) {
			sweet.save(function() {
				openRest("publicUser/signIn").post($scope.user, function(data) {
					$rootScope.currentUser = data;
					sweet.close();
					$state.go(Const.mainState);
				}, function(error) {
					sweet.error(error.data.message);
				});
			});
		}
	}

	$scope.login = function() {
		authenticate($scope.credentials).then(function(response) {
			if (response.data == Const.ok) {
				rest("publicUser/getCurrentUser").get(function(data) {
					if (data != null) {
						$rootScope.currentUser = data;

						/*if ($rootScope.currentUser.isTempPassword) {
							changePassword();
						}*/

						$state.go(Const.mainState);
					} else {
						$cookies.remove(Const.cookieToken);
					}
				});
			}
		}, function(error) {
			console.error(error.data);
		});
	}
});
