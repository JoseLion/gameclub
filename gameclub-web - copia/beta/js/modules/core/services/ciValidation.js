angular.module("Core").factory('ciValidation', function() {
	return function(value) {
		if (value.length != 10) {
			return false;
		} else {
			if (isNaN(value)) {
				return false;
			}

			if (parseFloat(value.substring(0, 2)) <= 0 || parseFloat(value.substring(0, 2)) > 24) {
				return false;
			} else {
				var verficationDigit = 0;
				var luhnArray = [];

				for (let i = 0; i < 9; i++) {
					if (i % 2 == 0) {
						var doubleValue = parseFloat(value[i]) * 2;
						if (doubleValue > 9) {
							var str = doubleValue.toString();
							doubleValue = parseFloat(str.substring(0, 1)) + parseFloat(str.substring(1, 2));
						}

						luhnArray.push(doubleValue);
					} else {
						luhnArray.push(parseFloat(value[i]));
					}
				}

				var sum = 0;
				for (let i = 0; i < luhnArray.length; i++) {
					sum += luhnArray[i];
				}

				if (sum % 10 != 0) {
					verficationDigit = 10 - (sum % 10);
				}

				if (verficationDigit != parseFloat(value.substring(9, 10))) {
					return false;
				} else {
					return true;
				}
			}
		}
	}
});
