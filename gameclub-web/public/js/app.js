angular.module('GameClub', [
	'ui.router',
	'oc.lazyLoad',
	'ui.select',
	'ngSanitize',
	'ngResource',
	'ngCookies',
	'ui.bootstrap',
	'oitozero.ngSweetAlert',
	'cgNotify',
	'720kb.socialshare',
	'Core',
	'Home',
	'Login',
	'Search',
	'Settings',
	'Profile',
	'MyGames',
	'Game',
	'TermsConditions',
	'LevelapBlog',
	'WorkForUs'
])
.config(['$locationProvider' ,'$httpProvider', function($locationProvider, $httpProvider, $http) {
	$httpProvider.defaults.withCredentials = true;

	$httpProvider.interceptors.push(['$rootScope', '$q', '$location', '$cookies', 'Const', function($rootScope, $q, $location, $cookies, Const) {
		return {
			request: function(config) {
				if ($cookies.get(Const.cookieToken)) {
					config.headers['X-' + Const.cookieToken] = $cookies.get(Const.cookieToken);
					config.headers['Content-Encoding'] = 'gzip';
				}

				return config;
			}
		};
	}]);
}])
.service('urlRestPath', function($location) {
	let port = '8090';
	if($location.$$port == 443) {
		port = '8390';
	}
	return {url: $location.$$protocol + '://' + $location.$$host + ':' + port + '/gameclub'};
});
