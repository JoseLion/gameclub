angular.module('Login').controller('LoginCtrl', function($scope, $rootScope, sweet, openRest, $state, Const, authenticate, rest, SweetAlert, $uibModal, notif) {
	$scope.user = {};
	$scope.credentials = {};
	$scope.passRegex = '^(?=.*[0-9])(?=.*[A-Z])([0-9-a-zA-Z]+)$';

	$rootScope.$watch("currentUser", function(newValue, oldValue) {
		if (newValue != null) {
			$state.go(Const.mainState);
		}
	});

	$scope.signIn = function() {
		if ($scope.user.terms) {
			sweet.save(function() {
				signIn($scope.user);
			});
		}
	}

	$scope.login = function() {
		logIn($scope,credentials);
	}

	$scope.fbSignIn = function() {
		$scope.isFbSingIn = true;

		FB.login(function(response) {
			if (response.authResponse != null) {
				FB.api('/me', {fields: 'name, email'}, function(me) {
					if (me.email == null) {
						FB.logout(function(logoutResponse) {
							notif.warning("El correo electrónico es necesario para crea una cuenta en Smartbid. Por favor permite el acceso a tu correo cuando inicies sesión con Facebook");
							$scope.isFbSingIn = false;
						}, response.authResponse.accessToken);
					} else {
						let user = {
							username: me.email,
							name: me.name,
							lastName: "",
							password: me.id,
							isFacebookUser: true
						};

						signIn(user);
					}
				});
			} else {
				$scope.isFbSingIn = false;
			}
		}, {
			scope: 'public_profile,email',
			auth_type: 'rerequest'
		});
	}

	$scope.fbLogIn = function() {
		$scope.isFbLogIn = true;

		FB.login(function(response) {
			if (response.authResponse != null) {
				FB.api('/me', {fields: 'name, email'}, function(me) {
					if (me.email == null) {
						FB.logout(function(logoutResponse) {
							notif.warning("El correo electrónico es necesario para crea una cuenta en Smartbid. Por favor permite el acceso a tu correo cuando inicies sesión con Facebook");
							$scope.isFbLogIn = false;
						}, response.authResponse.accessToken);
					} else {
						let credentials = {
							username: me.email,
							password: me.id
						};

						logIn(credentials);
					}
				});
			} else {
				$scope.isFbLogIn = false;
			}
		}, {
			scope: 'public_profile,email',
			auth_type: 'rerequest'
		});
	}

	function signIn(user) {
		openRest("publicUser/signIn").post(user, function() {
			SweetAlert.swal("Registro Exitoso", "Para completar el registro entra a tu correo y da click en el link de confirmación que hemos enviado", "success");
			$scope.isFbSingIn = false;
		}, function(error) {
			sweet.error(error.data.message);
			$scope.isFbSingIn = false;
		});
	}

	function logIn(credentials) {
		$scope.isLoginIn = true;

		authenticate(credentials).then(function(response) {
			if (response.data == Const.ok) {
				rest("publicUser/getCurrentUser").get(function(data) {
					if (data != null) {

						if (data.token == null) {
							$rootScope.currentUser = data;

							/*if ($rootScope.currentUser.isTempPassword) {
								changePassword();
							}*/
						} else {
							let modal = $uibModal.open({
								size: "md",
								backdrop: true,
								templateUrl: "accountVerification.html",
								controller: function($scope, $uibModalInstance, rest) {
									$scope.ok = function() {
										$uibModalInstance.dismiss();
									}

									$scope.resendVerification = function() {
										$scope.isLoading = true;
										rest("publicUser/resendVerification").get(function() {
											$scope.isLoading = false;
											notif.success("Correo reenviado con éxito");
											$scope.ok();
										});
									}
								},
								resolve: {}
							});

							modal.result.then(function() {
								$rootScope.logout();
							}, function() {
								$rootScope.logout();
							});
						}
					} else {
						$cookies.remove(Const.cookieToken);
					}

					$scope.isLoginIn = false;
					$scope.isFbLogIn = false;
				});
			}
		}, function(error) {
			notif.danger(error.data);
			$scope.isLoginIn = false;
			$scope.isFbLogIn = false;
		});
	}
});
