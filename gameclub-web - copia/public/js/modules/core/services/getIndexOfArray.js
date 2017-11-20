angular.module("Core").factory('getIndexOfArray', function() {
	return function(array, property, match) {
		let index = -1;

		if (array != null) {
			if (property == null) {
				for (let i = array.length - 1; i >= 0; i--) {
					if (array[i] == match) {
						index = i;
						break;
					}
				}
			} else {
				for (let i = array.length - 1; i >= 0; i--) {
					try {
						if (eval("array[i]." + property) == match) {
							index = i;
							break;
						}
					} catch(error) {
						index = -1;
						break;
					}
				}
			}
		}

		return index;
	}
});