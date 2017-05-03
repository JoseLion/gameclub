angular.module('GameClub').controller('MainCtrl', function($scope, $rootScope, $state, Const, $http, urlRestPath, rest, $cookies) {
	$http.get(urlRestPath.url + "/api/token").then(function(response) {
		if (response != null && response.data != null) {
			$rootScope.paddingLogged = {
				padding: '1.4em 0'
			};
			
			rest("publicUser/getCurrentUser").get(function(data) {
				if (!data.status || data.token != null) {
					$rootScope.logout();
				} else {
					if (data != null) {
						$rootScope.currentUser = data;

						/*if ($rootScope.currentUser.isTempPassword) {
							changePassword();
						}*/

						$rootScope.paddingLogged = {
							padding: '1em 0'
						};
					}
				}
			}, function(error) {
				$rootScope.logout();
			});
		}
	});

	$rootScope.logout = function() {
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
		});

		/*FB.getLoginStatus(function(response) {
			if (response.status === "connected") {
				FB.logout(function(response) {

				}, response.authResponse.accessToken);
			}
		});*/
	}

	$rootScope.link = {
		shareAndPlay : {
			name: 'Share & Play',
			route: 'gameclub.home({anchor:"how-it-works"})'
		},
		faq : {
			name: 'Preguntas',
			route: ''
		},
		blog : {
			name: 'Blog',
			route: 'gameclub.blog'
		},
		login : {
			name: 'Inicia sesión',
			route: 'gameclub.login'
		},
		account : {
			name: 'Pablo Ponce',
			route: 'gameclub.account'
		}
	};
	$rootScope.accountlinks = {
		profile : {
			name: 'Tu perfil',
			route: 'gameclub.account.profile'
		},
		settings : {
			name: 'Tu cuenta',
			route: 'gameclub.account.settings'
		},
		games : {
			name: 'Tus juegos',
			route: 'gameclub.account.games'
		},
		messages : {
			name: 'Mensajes',
			route: ''
		},
		getCoins : {
			name: 'Obtén coins',
			route: ''
		}
	};

	$rootScope.isLogged = false;

	// $http.get(urlRestPath.url + '/api/token').then(function(response) {
	// 	if (response != null && response.data != null) {
	// 		rest('publicUser/getCurrentUser').get(function(data) {
	// 			if (!data.status) {
	// 				$rootScope.logout();
	// 			} else {
	// 				if (data != null) {
	// 					$rootScope.currentUser = data;
	// 					$rootScope.$broadcast(Const.events.userLoggedIn);
	//
	// 					if ($rootScope.currentUser.isTempPassword) {
	// 						changePassword();
	// 					}
	// 				}
	// 			}
	// 		}, function(error) {
	// 			$rootScope.logout();
	// 		});
	// 	}
	// });

	// $scope.login = function() {
	// 	login();
	// }

	// $rootScope.logout = function() {
	// 	let request = {
	// 		method: 'POST',
	// 		url: urlRestPath.url + '/logout',
	// 		headers: {
	// 			'X-XSRF-TOKEN': $cookies.get(Const.cookieToken)
	// 		}
	// 	};
	//
	// 	$http(request).finally(function() {
	// 		$cookies.remove(Const.cookieToken, {path: '/'});
	// 		delete $rootScope.currentUser;
	// 		$state.go(Const.mainState);
	// 	});
	//
	// 	FB.getLoginStatus(function(response) {
	// 		if (response.status === 'connected') {
	// 			FB.logout(function(response) {
	//
	// 			}, response.authResponse.accessToken);
	// 		}
	// 	});
	// }

	// $scope.signIn = function() {
	// 	signIn();
	// }

	// $scope.changePassword = function() {
	// 	changePassword();
	// }

	// $scope.subscribe = function() {
	// 	return openRest('publicUser/subscribe').post({username:$scope.formSusbcribe.email}, function() {
	// 		toaster.success({body: Const.messages.successSent});
	// 		$scope.formSusbcribe = {};
	// 	}, function(error) {
	// 		toaster.warning({body: Const.messages.error});
	// 		$scope.formSusbcribe={};
	// 	});
	// }

});
