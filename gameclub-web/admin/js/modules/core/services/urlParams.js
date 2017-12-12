angular.module("Core").factory('urlParams', function(forEach) {
	return function(url, params) {
		if (params != null && Object.keys(params).length > 0) {
			let symbol = '?';

			forEach(params, function(obj, key) {
				url += symbol + key + "=" + obj;
				symbol = '&';
			});
		}

		return url;
	}
});