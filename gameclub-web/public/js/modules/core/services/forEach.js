angular.module('Core').factory('forEach', function() {
	return function(array, doWork, reverse) {
		if (reverse !== true) {
			reverse = false;
		}

		if (array != null) {
			if (Array.isArray(array)) {
				if (reverse) {
					for (var i = array.length - 1; i >= 0; i--) {
						let response = doWork(array[i], i);

						if (response === "break") {
							break;
						}
					}
				} else {
					for (let i = 0; i < array.length; i++) {
						let response = doWork(array[i], i);

						if (response === "break") {
							break;
						}
					}
				}
			} else if (typeof array === "object") {
				for (key in array) {
					let response = doWork(array[key], key);

					if (response === "break") {
						break;
					}
				}
			}
		}
	};
});