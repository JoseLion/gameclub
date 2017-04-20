angular.module("GameClub").controller('MainCtrl', function($rootScope, $state, Const) {

	$rootScope.link = {
		shareAndPlay : {
			name: 'Share & Play',
			route: ''
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
			route: ''
		}
	};


	// $http.get(urlRestPath.url + "/api/token").then(function(response) {
	// 	if (response != null && response.data != null) {
	// 		rest("publicUser/getCurrentUser").get(function(data) {
	// 			if (!data.status) {
	// 				$scope.logout();
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
	// 			$scope.logout();
	// 		});
	// 	}
	// });

	// $scope.login = function() {
	// 	login();
	// }

	// $scope.logout = function() {
	// 	let request = {
	// 		method: 'POST',
	// 		url: urlRestPath.url + '/logout',
	// 		headers: {
	// 			'X-XSRF-TOKEN': $cookies.get(Const.cookieToken)
	// 		}
	// 	};
	//
	// 	$http(request).finally(function() {
	// 		$cookies.remove(Const.cookieToken, {path: "/"});
	// 		delete $rootScope.currentUser;
	// 		$state.go(Const.mainState);
	// 	});
	//
	// 	FB.getLoginStatus(function(response) {
	// 		if (response.status === "connected") {
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
	// 	return openRest("publicUser/subscribe").post({username:$scope.formSusbcribe.email}, function() {
	// 		toaster.success({body: Const.messages.successSent});
	// 		$scope.formSusbcribe = {};
	// 	}, function(error) {
	// 		toaster.warning({body: Const.messages.error});
	// 		$scope.formSusbcribe={};
	// 	});
	// }

});
