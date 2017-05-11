angular.module('TermsConditions').controller('TermsConditionsCtrl', function($scope, $rootScope, user, isFacebook, Const, $state, openRest, notif, sweet) {

    $scope.terms = {};

    $scope.showConfirmation = true;
    if(user == null && isFacebook == null) {
        $scope.showConfirmation = false;
    }
    $scope.ok = function() {
        console.log($scope.terms.accepted)
        if ($scope.terms.accepted) {
            $scope.isSaving = true;

            if(user != null) {
                signIn(user);
            } else if(isFacebook != null && isFacebook) {
                FB.login(function(response) {
    				if (response.authResponse != null) {
    					FB.api('/me', {fields: 'name, email'}, function(me) {
    						if (me.email == null) {
    							FB.logout(function(logoutResponse) {
    								notif.warning("El correo electrónico es necesario para crea una cuenta en Smartbid. Por favor permite el acceso a tu correo cuando inicies sesión con Facebook");
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
    				}
    			}, {
    				scope: 'public_profile,email',
    				auth_type: 'rerequest'
    			});
            }
        } else {
            notif.warning(Const.messages.acceptTerms);
        }
    };

    function signIn(user) {
		openRest("publicUser/signIn").post(user, function() {
            notif.success(Const.messages.success);
            $state.go('gameclub.home');
		}, function(error) {
			sweet.error(error.data.message);
		});
	}

});
