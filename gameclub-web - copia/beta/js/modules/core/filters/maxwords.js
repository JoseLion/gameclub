angular.module("Core").filter("maxwords", function() {
	return function(value, max) {
		if (!value) {
			return '';
		}

		if (!max) {
			return value;
		}

		let split = value.split(" ");

		if (split.length > max) {
			let result = "";

			for (let i = 0; i < max; i++) {
				result += split[i] + " ";
			}

			result = result.trim() + "...";

			return result;
		}

		return value;
	}
});