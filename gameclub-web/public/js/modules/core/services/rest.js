angular.module('Core').factory('rest', function($resource, $q, $rootScope, $cookies, $location, urlRestPath, forEach, Const, notif) {
	let baseUrl = urlRestPath.url + "/api/";

	let resourceInterceptor = {
		response: function(response) {
			return response.data;
		},

		responseError: function(response) {
			if (response.status == 403 && $rootScope.currentUser != null) {
				$rootScope.currentUser = null;
			}

			if (response.status <= 0) {
				response.data = {message: Const.messages.unableToConnect, status: 500};
			}

			if (response.data == null || (response.data != null && !response.data.custom)) {
				notif.danger("Oops, algo salió mal... Por favor vuelve a inténtarlo más tarde");
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
