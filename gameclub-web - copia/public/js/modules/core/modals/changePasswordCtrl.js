angular.module("Core").controller('ChangePasswordCtrl', function($scope, $uibModalInstance, sweet, rest, notif) {
	$scope.password = {};
	$scope.passRegex = '^(?=.*[0-9])(?=.*[A-Z])([0-9-a-zA-Z]+)$';

	$scope.cancel = function() {
		$uibModalInstance.dismiss();
	}

	$scope.ok = function() {
		sweet.default("Tu contraseña se cambiará permanentemente", function() {
			rest("publicUser/changePassword").post($scope.password, function(data) {
				notif.success("La contraseña se cambió con éxito");
				sweet.close();
				$uibModalInstance.close(data);
			}, function(error) {
				sweet.error(error.data != null ? error.data.message : error);
			});
		});
	}
});