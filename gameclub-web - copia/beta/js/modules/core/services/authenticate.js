angular.module("Core").factory('authenticate', function(urlRestPath, Const, $http) {
	return function(credentials) {
		let authHeader = {};

		if (credentials) {
			authHeader[Const.authHeader] = Const.authHeaderPrefix + btoa(credentials.username + Const.authHeaderSeparator + credentials.password);
			authHeader[Const.extraHeader] = Const.publicUser;
		}

		return $http.get(urlRestPath.url + '/api/user', {headers: authHeader});
	}
});