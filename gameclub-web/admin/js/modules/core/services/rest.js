angular.module('Core').factory('rest', function($resource, $q, $rootScope, $cookies, $location, notif, urlRestPath, forEach, Const) {
	let baseUrl = urlRestPath.url + "/api/";

	let resourceInterceptor = {
		response: function(response) {
			return response.data;
		},

		responseError: function(response) {
			if (response.status == 403 && $rootScope.loggedUser != null) {
				notif.danger(Const.messages.expired);
				$rootScope.loggedUser = null;
				$location.path("/login");
			}

			if (response.status == 401 || (response.status == 403 && $rootScope.loggedUser == null)) {
				notif.danger(Const.messages.unauthorized);
				$location.path("/login");
			}

			if (response.status == 500 && response.data.message == "Unauthorized") {
				$location.path("/login");
			}

			if (response.status <= 0) {
				response.data = {message: Const.messages.unableToConnect, status: 500};
			}

			if (response.status == 406) {
				if (response.data.message == "No message available") {
					notif.danger("Error " + response.data.status + ": " + response.data.error);
				} else {
					notif.danger(response.data.message);
				}
			}

			if (!response.data.custom && response.status != 403 && response.status != 401) {
				if (response.data.message == "No message available") {
					notif.danger("Error " + response.data.status + ": " + response.data.error);
				} else {
					notif.danger(response.data.message);
				}
			}

			return $q.reject(response);
		}
	};

	return function(action, isArray) {
		if (isArray == null) {
			isArray = false;
		}

		return $resource(baseUrl + action + '/:params', {params: '@_params'}, {
			get: {
				method:"GET",
				interceptor: resourceInterceptor,
				isArray: isArray
			},

			post: {
				method:"POST",
				interceptor: resourceInterceptor,
				transformRequest: compressedData,
				isArray: isArray
			},

			multipart: {
				method:"POST",
				interceptor: resourceInterceptor,
				transformRequest: formDataObject,
				headers: {'Content-Type': undefined},
				enctype: 'multipart/form-data',
				isArray: isArray
			},

			delete: {
				method:"DELETE",
				interceptor: resourceInterceptor,
				isArray: isArray
			},

			download:{
				method:"POST",
				interceptor: resourceInterceptor,
				transformRequest: compressedData,
				responseType: 'arraybuffer'
			}
		});
	};

	function formDataObject(data) {
		let fd = new FormData();

		forEach(data, function(value, key) {
			if (value instanceof Array && value.length > 0 && value[0] instanceof Blob) {
				forEach(value, function(v, k) {
					fd.append(key, v);
				});
			} else {
				if (value instanceof Blob) {
					fd.append(key, value);
				} else {
					fd.append(key, new Blob([JSON.stringify(value)], {type: "application/json"}));
				}
			}
		});

		return fd;
	}

	function compressedData(data) {
		if (data != null) {
			let gzip = pako.gzip(JSON.stringify(data));
			return gzip;
		} else {
			return null;
		}
	}
});
