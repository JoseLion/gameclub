angular.module("Gameclub").controller('MainCtrl', function($scope, $rootScope, $state, urlRestPath, $cookies, Const, $http, rest, $uibModal, forEach) {
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

	$rootScope.$watch("currentUser", function(newValue, oldValue) {
		if (newValue != null) {
			$scope.navigationArray = null;
			
			if (newValue.profile.wildcard) {
				rest("navigation/findAll", true).get(function(data) {
					setTimeout(function() {
						$scope.$apply(function() {
							$scope.navigationArray = data;
						});
					}, 0);
				});
			} else {
				let level = 0;
				let array = [];

				for (let i = newValue.profile.crossNavigation.length - 1; i >= 0; i--) {
					let cross = newValue.profile.crossNavigation[i];
					cross.navigation.children = [];

					if (cross.navigation.level == level) {
						array.push(cross.navigation);
						newValue.profile.crossNavigation.splice(i, 1);
					}
				}

				level++;
				while(newValue.profile.crossNavigation.length > 0) {
					for (let i = newValue.profile.crossNavigation.length - 1; i >= 0; i--) {
						let cross = newValue.profile.crossNavigation[i];

						if (cross.navigation.level == level) {
							let parent = findInTree(array, cross.navigation.parentId);

							if (parent != null) {
								parent.children.push(cross.navigation);
								newValue.profile.crossNavigation.splice(i, 1);
							}
						}
					}

					level++;
				}

				$scope.navigationArray = array;
			}
		}
	});

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

	$scope.setLevelClass = function(level) {
		switch(level) {
			case 0:
				return 'nav-second-level';

			case 1:
				return 'nav-third-level';

			case 2:
				return 'nav-fourth-level';

			default:
				return '';
		}
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

	function findInTree(array, id) {
		let found;

		forEach(array, function(node) {
			if (node.id == id) {
				found = node;
				return 'break';
			}

			if (node.children.length > 0) {
				found = findInTree(node.children, id);

				if (found != null) {
					return 'break';
				}
			}
		});

		return found;
	}

	// ------------------ PROTOYPES ------------------

	Array.prototype.last = function() {
		return this[this.length - 1];
	}
});