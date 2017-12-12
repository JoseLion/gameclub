angular.module("Core").factory('ciValidation', function() {
	return function(value) {
		if (value.length != 10 && value.length != 13) {
			return false;
		} else if (value.length == 10) {
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
		} else if (value.length == 13) {
			var isValid = false;
			var d = [0,0,0,0,0,0,0,0,0,0];
			var sos9 = [4,3,2,7,6,5,4,3,2], sos6 = [3,2,7,6,5,4,3,2,0], sos = [2,1,2,1,2,1,2,1,2];
			var suma = 0;

			var prov = parseInt(value.substring(0, 2));

			if(prov > 0 && prov <= 24){
				console.log("");
				for (var i = 0; i < d.length; i++) {
					d[i] = value.charAt(i);
				}
				if(d[2]== 9){
					for (var i = 0; i < d.length-1; i ++) {
						suma += (d[i] * sos9[i]);
					}
					if (suma%11 != 0) {
						if((11 - (suma%11)) == d[9]){
							isValid = true;
						} else{
							isValid = false;
						}
					} else {
						if ((suma%11) == d[9]) {
							isValid = true;
						} else{
							isValid = false;
						}
					}
				}

				else if(d[2]== 6){				
					for (var i = 0; i < d.length-1; i ++) {
						suma += (d[i] * sos6[i]);
					}
					if (suma%11 != 0) {
						if((11 - (suma%11)) == d[8]){
							isValid = true;
						} else{
							isValid = false;
						}
					} else {
						if ((suma%11) == d[8]) {
							isValid = true;
						} else{
							isValid = false;
						}
					}
				} 

				else if(d[2] == 0 || d[2] == 1 ||d[2] == 2 || d[2] == 3 || d[2] == 4 || d[2] == 5){
					for (var i = 0; i < d.length-1; i++) {
						if (d[i] * sos[i] > 9) {
							suma += (d[i] * sos[i]) - 9;
						} else{
							suma += d[i] * sos[i];
						}
					}
					if (suma%10 != 0) {
						if((10 - (suma%10)) == d[9]){
							isValid = true;
						} else{
							isValid = false;
						}
					} else {
						if ((suma%10) == d[9]) {
							isValid = true;
						} else{
							isValid = flase;
						}
					}
				} else{
					isValid = false;
				}
			}
			return isValid;
		}
	}
});
