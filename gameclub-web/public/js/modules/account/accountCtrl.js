angular.module("GameClub").controller('AccountCtrl', function($scope, token, $state, Const, changePassword) {
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
});