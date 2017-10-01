angular.module("GameClub").controller('AccountCtrl', function($scope, $rootScope, token, gamesSummary, $state, Const, changePassword, sweet, rest, notif, $http, urlRestPath, $cookies) {
	token.$promise.then(function(data) {
		if ($state.current.name == 'gameclub.account') {
			$state.go('gameclub.account.profile');
		}

		gamesSummary.$promise.then(function(data) {
			$scope.summary = data;
		});
	}, function(error) {
		$state.go(Const.mainState);
	});

	$scope.changePassword = function() {
		changePassword();
	}

	$scope.deleteAccount = function() {
		sweet.default("Se eliminará su cuenta completamente. Este porceso es irreversible", function() {
			rest("publicUser/deleteAccount").delete(function() {
				let request = {
					method: 'POST',
					url: urlRestPath.url + '/logout',
					headers: {
						'X-XSRF-TOKEN': $cookies.get(Const.cookieToken)
					}
				};

				$http(request).finally(function() {
					$cookies.remove(Const.cookieToken, {path: "/"});
					delete $rootScope.currentUser;
					$state.go(Const.mainState);

					notif.success("Su cuenta se eliminó con éxito");
					sweet.close();
				});
			}, function(error) {
				sweet.close();
			});
		});
	}
});