angular.module('GameClub').controller('MainCtrl', function($scope, $rootScope, $state, Const, $http, urlRestPath, rest, $cookies, openRest, forEach, $location, $anchorScroll) {
	$http.get(urlRestPath.url + "/api/token").then(function(response) {
		if (response != null && response.data != null) {
			$rootScope.paddingLogged = {
				padding: '1.4em 0'
			};

			rest("publicUser/getCurrentUser").get(function(data) {
				if (!data.status) {
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

	/************** Call Settings Table *****************/
	openRest("settings/findAll").get(function(data) {
		$rootScope.settings = data;
	});

	openRest("category/findAll", true).get(function(data) {
		$rootScope.categories = data;
	});

	openRest("console/findAll", true).get(function(data) {
		$rootScope.consoles = data;
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

	$scope.goToAccount = function() {
		angular.element('#myNavbar').collapse('hide');
		if (!$state.includes("gameclub.account")) {
			$state.go("gameclub.account.profile");
		}
	}

	/******** Método para anclar a una seccion con id  ********/
	$rootScope.gotoAnchor = function(route,idAnchor) {
		angular.element('#myNavbar').collapse('hide');
  		$state.go(route).then(function() {
  			$location.hash(idAnchor);
  			$anchorScroll();
  		});
    }

	$rootScope.link = {
		shareAndPlay: {
			name: 'Share & Play',
			route: 'gameclub.home({anchor:"how-it-works"})'
		},
		faq: {
			name: 'Preguntas',
			route: 'gameclub.faqs'
		},
		blog: {
			name: 'Blog',
			route: 'levelapBlog.blog.home'
		},
		login: {
			name: 'Inicia sesión',
			route: 'gameclub.login'
		},
		account: {
			name: '',
			route: 'gameclub.account'
		},
		termsConditions : {
			name: 'Términos y Condiciones',
			route: 'gameclub.termsConditions({user:user, isFacebook:isFacebook})'
		},
		workForUs: {
			name: 'Trabaja con nosotros',
			route: 'gameclub.workForUs'
		},
		contactUs: {
			name: 'Contáctanos',
			route: 'gameclub.contactUs'
		}
	};

});
