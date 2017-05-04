angular.module('Core').directive("ciRequired", function() {
	return {
		restrict: 'A',
		scope: {
			ciRequired: "=?"
		},
		require: 'ngModel',
		link: function($scope, element, attrs, ctrl) {
			$scope.$watch('ciRequired', function(newValue, oldValue) {
				if (newValue == null) {
					$scope.ciRequired = true;
				} else {
					if (typeof newValue != 'boolean') {
						$scope.ciRequired = false;
					} else {
						$scope.ciRequired = newValue;
					}
				}

				ctrl.$validate();
			});

			ctrl.$validators.ciRequired = function(modelValue, viewValue) {
				if ($scope.ciRequired) {
					if (modelValue != null) {
						if (modelValue === "") {
							return true;
						}
						
						if (modelValue.length != 10) {
							return false;
						} else {
							if (isNaN(modelValue)) {
								return false;
							}

							if (parseFloat(modelValue.substring(0, 2)) <= 0 || parseFloat(modelValue.substring(0, 2)) > 24) {
								return false;
							} else {
								var verficationDigit = 0;
								var luhnArray = [];

								for (let i = 0; i < 9; i++) {
									if (i % 2 == 0) {
										var doubleValue = parseFloat(modelValue[i]) * 2;
										if (doubleValue > 9) {
											var str = doubleValue.toString();
											doubleValue = parseFloat(str.substring(0, 1)) + parseFloat(str.substring(1, 2));
										}

										luhnArray.push(doubleValue);
									} else {
										luhnArray.push(parseFloat(modelValue[i]));
									}
								}

								var sum = 0;
								for (let i = 0; i < luhnArray.length; i++) {
									sum += luhnArray[i];
								}

								if (sum % 10 != 0) {
									verficationDigit = 10 - (sum % 10);
								}

								if (verficationDigit != parseFloat(modelValue.substring(9, 10))) {
									return false;
								} else {
									return true;
								}
							}
						}
					} else {
						return true;
					}
				} else {
					return true;
				}
			}
		}
	};
});