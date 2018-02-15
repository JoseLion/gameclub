angular.module("Core").factory('addPaymentezCard', function($rootScope, $location, $cookies, Const) {
	return function() {
		let today = new Date();
        let token = "application_code=" + gcProperties.paymentez.appCode +
                    "&email=" + encodeURIComponent($rootScope.currentUser.username) +
                    "&failure_url=" + encodeURIComponent($location.$$absUrl) +
                    "&response_type=redirect" +
                    "&session_id=" + $cookies.get(Const.cookieToken) +
                    "&success_url=" + encodeURIComponent($location.$$absUrl) +
                    "&uid=" + $rootScope.currentUser.id +
                    "&" + today.getTime() +
                    "&" + gcProperties.paymentez.appKey;

        let url = gcProperties.paymentez.baseUrl + "/api/cc/add/?" +
                "application_code=" + gcProperties.paymentez.appCode +
                "&uid=" + $rootScope.currentUser.id +
                "&email=" + encodeURIComponent($rootScope.currentUser.username) +
                "&session_id=" + $cookies.get(Const.cookieToken) +
                "&auth_timestamp=" + today.getTime() +
                "&auth_token=" + sha256(token) +
                "&response_type=redirect" +
                "&success_url=" + encodeURIComponent($location.$$absUrl) +
                "&failure_url=" + encodeURIComponent($location.$$absUrl);
        
        if ($rootScope.currentUser.contactPhone != null && $rootScope.currentUser.contactPhone != '') {
            url += "&buyer_phone=" + $rootScope.currentUser.contactPhone;
        }

        window.open(url, "_self");
	}
});