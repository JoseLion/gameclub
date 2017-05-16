angular.module("GameClub").controller('AccountCtrl', function($scope, $rootScope, token, $state, Const, changePassword, sweet, rest, notif) {
	token.$promise.then(function(data) {
		if ($state.current.name == 'gameclub.account') {
			$state.go('gameclub.account.profile');
		}
	}, function(error) {
		$state.go(Const.mainState);
	});

	$scope.changePassword = function() {
		changePassword();
	}

	$scope.deleteAccount = function() {
		sweet.default("Se eliminará su cuenta completamente. Este porceso es irreversible", function() {
			rest("publicUser/deleteAccount").delete(function() {
				$state.go(Const.mainState);
				notif.success("Su cuenta se eliminó con éxito");
				sweet.close();
				$rootScope.currentUser = null;
			}, function(error) {
				sweet.close();
			});
		});
	}
});