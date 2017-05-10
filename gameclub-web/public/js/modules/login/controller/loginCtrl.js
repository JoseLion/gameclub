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
		} else {
			notif.warning(Const.messages.acceptTerms);
		}
	}

	$scope.login = function() {
		logIn($scope.credentials);
	}

	$scope.fbSignIn = function() {
		$scope.isFbSingIn = true;

		let modal = $uibModal.open({
			size: "md",
			backdrop: true,
			templateUrl: "facebookSignIn.html",
			controller: function($scope, $uibModalInstance, notif, Const) {
				$scope.terms = {};

				$scope.ok = function() {
					if ($scope.terms.status) {
						$uibModalInstance.close();
					} else {
						notif.warning(Const.messages.acceptTerms);
					}
				}

				$scope.cancel = function() {
					$uibModalInstance.dismiss();
				}
			},
			resolve: {}
		});

		modal.result.then(function() {
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
								password: me.userID,
								isFacebookUser: true,
								facebookToken: response.authResponse.accessToken,
								facebookName: me.name
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
		}, function() {
			$scope.isFbSingIn = false;
		});
	}

	$scope.fbLogIn = function() {
		$scope.isFbLogIn = true;

		FB.login(function(response) {
			if (response.authResponse != null) {
				FB.api('/me', {fields: 'name, email'}, function(me) {
					if (me.email == null) {
						FB.logout(function(logoutResponse) {
							notif.warning("El correo electrónico es necesario para crea una cuenta en Game Club. Por favor permite el acceso a tu correo cuando inicies sesión con Facebook");
							$scope.isFbLogIn = false;
						}, response.authResponse.accessToken);
					} else {
						let credentials = {
							username: me.email,
							password: me.userID
						};

						logIn(credentials);

						$rootScope.currentUser.facebookToken = response.authResponse.accessToken;
						let formData = {
							user: $rootScope.currentUser
						};

						rest("publicUser/save").multipart(formData, function(data) {
							$rootScope.currentUser = data;
						}, function(error) {});
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

	$scope.forgotPassword = function() {
		$uibModal.open({
			size: "md",
			backdrop: true,
			templateUrl: "forgotPassword.html",
			controller: function($scope, $uibModalInstance, sweet, Const, urlRestPath, notif, $http) {
				$scope.forgot = {};

				$scope.ok = function() {
					sweet.default("Se reestablecerá tu contraseña", function() {
						let authHeader = {};
						authHeader[Const.authHeader] = Const.authHeaderPrefix + btoa($scope.forgot.email + Const.authHeaderSeparator + Const.authHeaderSeparator + "true");
						authHeader[Const.extraHeader] = Const.publicUser;

						let http = $http.get(urlRestPath.url + '/api/user', {headers: authHeader}).then(function(response) {
							notif.success("Tu contraseña ha sido reestablecida. Por favor dirijete a tu correo electrónico");
							swal.close();
							$uibModalInstance.close();
						}, function(error) {
							if (error.status > 0) {
								notif.danger(error.data);
							} else {
								notif.danger(Const.messages.unableToConnect);
							}

							swal.close();
						});
					});
				}

				$scope.cancel = function() {
					$uibModalInstance.dismiss();
				}
			},
			resolve: {}
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
			console.log("error: ", error);
			if (error.status == -1) {
				notif.danger(Const.messages.unableToConnect);
			} else {
				notif.danger(error.data);
			}
			
			$scope.isLoginIn = false;
			$scope.isFbLogIn = false;
		});
	}
});
