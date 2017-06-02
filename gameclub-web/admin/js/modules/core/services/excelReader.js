angular.module("Core").factory('excelReader', function($q) {
	let service = function(data) {
		angular.extend(this, data);
	}

	service.read = function(file) {
		let deferred = $q.defer();

		XLSXReader(file, true, true, function(data) {
			deferred.resolve(data);
		});

		return deferred.promise;
	}

	service.readNoJson = function(file) {
		let deferred = $q.defer();

		XLSXReader(file, true, false, function(data) {
			deferred.resolve(data);
		});

		return deferred.promise;
	}

	return service;
});