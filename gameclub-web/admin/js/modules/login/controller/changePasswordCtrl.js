angular.module("Login").controller('ChangePasswordCtrl', function($scope, $uibModalInstance, sweet, rest, notif) {
	$scope.passwordObj = {};
	$scope.passRegex = '^(?=.*[0-9])(?=.*[A-Z])([0-9-a-zA-Z]+)$';
	$scope.passwordTooltip = "La contraseña debe tener únicamente letras y números, mínimo 6 caracteres, con al menos una mayúscula y un número";

	$scope.cancel = function() {
		$uibModalInstance.dismiss();
	}

	$scope.ok = function() {
		if ($scope.passwordObj.password === $scope.passwordObj.repeat) {
			sweet.default("Se cambiará su contraseña", function() {
				rest("adminUser/changePassword").post($scope.passwordObj, function() {
					notif.success("La contraseña se cambió con éxito");
					swal.close();
					$uibModalInstance.close();
				}, function(error) {
					sweet.error(error.data.message);
				})
			});
		} else {
			notif.warning("Las contraseñas no coinciden. Vuelva a intentarlo");
		}
	}
});