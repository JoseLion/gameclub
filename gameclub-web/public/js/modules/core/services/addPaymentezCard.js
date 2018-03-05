angular.module("Core").factory('addPaymentezCard', function($rootScope, $sce) {
	return function() {
		let today = new Date();
        let token = "application_code=" + gcProperties.paymentez.appCode +
                    "&email=" + encodeURIComponent($rootScope.currentUser.username) +
                    "&session_id=" + gcProperties.paymentez.sessionID +
                    "&uid=" + $rootScope.currentUser.id +
                    "&" + today.getTime() +
                    "&" + gcProperties.paymentez.appKey;

        let url = gcProperties.paymentez.baseUrl + "/api/cc/add/?" +
                "application_code=" + gcProperties.paymentez.appCode +
                "&uid=" + $rootScope.currentUser.id +
                "&email=" + encodeURIComponent($rootScope.currentUser.username) +
                "&session_id=" + gcProperties.paymentez.sessionID +
                "&auth_timestamp=" + today.getTime() +
                "&auth_token=" + sha256(token);
        
        if ($rootScope.currentUser.contactPhone != null && $rootScope.currentUser.contactPhone != '') {
            url += "&buyer_phone=" + $rootScope.currentUser.contactPhone;
        }

        return $sce.trustAsResourceUrl(url);
	}
});