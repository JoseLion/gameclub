angular.module('Settings').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'settings', {
		url: '/settings?error_value',
		templateUrl: 'js/modules/account/settings/view/settings.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'SettingsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Settings',
					files: ['js/modules/account/settings/controller/settingsCtrl.js', 'js/modules/account/settings/style/settings.less', 'js/modules/account/settings/style/settings.responsive.less']
				}]);
			},

			reviews: function(rest) {
				return rest("review/getReviewsOfCurrentUser/:page").get({page: 0});
			},

			addCardError: function($stateParams) {
				return $stateParams.error_value;
			},

			cardsList: function($rootScope, $http, Const, $cookies, $sce) {
				/*if ($rootScope.currentUser != null) {
					let today = new Date();
					let token = "application_code=" + Const.paymentez.appCode +
								"&email=" + encodeURIComponent($rootScope.currentUser.username) +
								"&session_id=" + $cookies.get(Const.cookieToken) +
								"&uid=" + $rootScope.currentUser.id +
								"&" + today.getTime() +
								"&" + Const.paymentez.appKey;
					let url = $sce.trustAsResourceUrl(Const.paymentez.baseUrl + "/api/cc/list" +
						"?application_code=" + Const.paymentez.appCode +
						"&uid=" + $rootScope.currentUser.id +
						"&email=" + encodeURIComponent($rootScope.currentUser.username) + 
						"&session_id=" + $cookies.get(Const.cookieToken) +
						"&auth_timestamp=" + today.getTime() +
						"&auth_token=" + sha256(token));

					return $http.get(url, {withCredentials: false});
				}*/
				
				return null;
			}
		}
	});

});
