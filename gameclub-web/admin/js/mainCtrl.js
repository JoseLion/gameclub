angular.module("Gameclub").controller('MainCtrl', function($scope, $rootScope, $state, urlRestPath, $cookies, Const, $http, rest, $uibModal) {
	if ($rootScope.currentUser == null) {
		rest("adminUser/getCurrentUser").get(function(data) {
			$rootScope.currentUser = data;

			if ($rootScope.currentUser.hasTempPassword) {
				let modal = changePass();

				modal.result.then(function() {}, function() {
					$scope.logout();
				});
			}
		});
	}

	$scope.logout = function() {
		let request = {
			method: 'POST',
			url: urlRestPath.url + '/logout',
			headers: {
				'X-XSRF-TOKEN': $cookies.get(Const.cookieToken)
			}
		};

		$http(request).finally(function() {
			$cookies.remove(Const.cookieToken, {path: "/"});
			delete $rootScope.redirect;
			$rootScope.currentUser = null;
			$state.go(Const.loginState);
		});
	}

	$scope.$on("logout", function() {
		$scope.logout();
	});

	$scope.changePassword = function() {
		changePass();
	}

	function changePass() {
		return $uibModal.open({
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
	}

	// ------------------ PROTOYPES ------------------

	Array.prototype.last = function() {
		return this[this.length - 1];
	}
});