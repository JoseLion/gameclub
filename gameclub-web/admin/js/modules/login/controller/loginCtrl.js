angular.module("Login").controller('LoginCtrl', function($scope, $rootScope, $cookies, Const, authenticate, notif, $state, urlRestPath, $http, rest, $uibModal) {
	$scope.credentials = {};

	$scope.login = function() {
		$cookies.remove(Const.cookieToken);

		authenticate($scope.credentials).then(function(response) {
			if (response.data == Const.ok) {
				rest("adminUser/getCurrentUser").get(function(data) {
					$rootScope.currentUser = data;
					let state = $rootScope.redirect != null && $rootScope.redirect != Const.loginState ? $rootScope.redirect : Const.mainState;
					$state.go(state);

					if (data.hasTempPassword) {
						let modal = $uibModal.open({
							size: 'md',
							backdrop: 'static',
							templateUrl: "js/modules/login/view/changePasswordModal.html",
							controller: "ChangePasswordCtrl",
							resolve: {
								loadPlugin: function($ocLazyLoad) {
									return $ocLazyLoad.load([{
										name: 'Login',
										files: ['js/modules/login/controller/changePasswordCtrl.js']
									}]);
								}
							}
						});

						modal.result.then(function(userData) {
							$rootScope.currentUser = null;

							setTimeout(function() {
								$scope.$apply(function() {
									$rootScope.currentUser = userData;
								});
							}, 100);
						}, function() {
							$rootScope.$broadcast("logout");
						});
					}
				});
			}
		}, function(error) {
			if (error.status > 0) {
				notif.danger(error.data);
			} else {
				notif.danger(Const.messages.unableToConnect);
			}
		});
	}

	$scope.forgotPassword = function() {
		$uibModal.open({
			size: 'md',
			templateUrl: "forgotPassword.html",
			controller: function($scope, $uibModalInstance, sweet, rest, notif) {
				$scope.form = {};

				$scope.cancel = function() {
					$uibModalInstance.dismiss();
				}

				$scope.ok = function() {
					sweet.default("Se reestablecerá su contraseña", function() {
						let authHeader = {};
						authHeader[Const.authHeader] = Const.authHeaderPrefix + btoa($scope.form.email + Const.authHeaderSeparator + Const.authHeaderSeparator + "true");
						authHeader[Const.extraHeader] = Const.adminUser;

						let http = $http.get(urlRestPath.url + '/api/user', {headers: authHeader}).then(function(response) {
							notif.success("Su contraseña ha sido reestablecida. Por favor dirijase a su correo electrónico");
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
			}
		});
	}
});