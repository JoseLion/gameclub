angular.module('GameClub').controller('MainCtrl', function($scope, $rootScope, $state, Const, $http, urlRestPath, rest, $cookies, openRest, forEach, getImageBase64) {
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
	}, function(error) {
		$rootScope.currentUser = null;
	});

	openRest("category/findAll", true).get(function(data) {
		$rootScope.categories = data;

		forEach($rootScope.categories, function(category) {
			openRest("archive/downloadFile").download({name: category.whiteVector.name, module: category.whiteVector.module}, function(data) {
				category.whiteBase64 = getImageBase64(data, category.whiteVector.type);
			});

			openRest("archive/downloadFile").download({name: category.blackVector.name, module: category.blackVector.module}, function(data) {
				category.blackBase64 = getImageBase64(data, category.blackVector.type);
			});
		});
	});

	openRest("console/findAll", true).get(function(data) {
		$rootScope.consoles = data;

		forEach($rootScope.consoles, function(cnsl) {
			openRest("archive/downloadFile").download({name: cnsl.logo.name, module: cnsl.logo.module}, function(data) {
				cnsl.base64 = getImageBase64(data, cnsl.logo.type);
			});
		});
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
});
