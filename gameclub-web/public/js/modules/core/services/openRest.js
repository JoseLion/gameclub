angular.module('Core').factory('openRest', function($resource, $q, $rootScope, urlRestPath, forEach) {
	let baseUrl = urlRestPath.url + "/open/";

	let resourceInterceptor = {
		response: function(response) {
			return response.data;
		},

		responseError: function(response) {
			if (response.status == 500 && !response.data.custom) {
				/*toaster.pop({
					type: 'warning',
					title: 'Oops, algo salió mal...',
					body: 'Por favor vuelve a inténtarlo más tarde',
					showCloseButton: true
				});*/
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
