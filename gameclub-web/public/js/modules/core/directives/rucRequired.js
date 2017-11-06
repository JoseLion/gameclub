angular.module('Core').directive("rucRequired", function() {
	return {
		restrict: 'A',
		scope: {
			rucRequired: "=?"
		},
		require: 'ngModel',
		link: function($scope, element, attrs, ctrl) {
			$scope.$watch('rucRequired', function(newValue, oldValue) {
				if (newValue == null) {
					$scope.rucRequired = true;
				} else {
					if (typeof newValue != 'boolean') {
						$scope.rucRequired = false;
					} else {
						$scope.rucRequired = newValue;
					}
				}

				ctrl.$validate();
			});

			ctrl.$validators.rucRequired = function(modelValue, viewValue) {
				if ($scope.rucRequired) {
					if (modelValue != null) {
						if (modelValue === "") {
							return true;
						}
						
						if (modelValue.length != 13) {
							return false;
						} else {
							if (isNaN(modelValue)) {
								return false;
							} else {
								if (parseFloat(modelValue.substring(0, 2)) <= 0 || parseFloat(modelValue.substring(0, 2)) > 24) {
									return false;
								}

								if (parseFloat(modelValue.substring(2, 3)) == 7 || parseFloat(modelValue.substring(2, 3)) == 8) {
									return false;
								}

								//Persona Natural
								if (parseFloat(modelValue.substring(2, 3)) >= 0 && parseFloat(modelValue.substring(2, 3)) < 6) {
									if (parseFloat(modelValue.substring(10, 13)) == "000") {
										return false;
									}

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
									}
								}

								// Personas juridicas
								if (parseFloat(modelValue.substring(2, 3)) == 9) {
									if (parseFloat(modelValue.substring(10, 13)) == "000") {
										return false;
									}

									var verficationDigit = 0;
									var sum = 0;
									var multiplier = 4;

									for (let i = 0; i < 9; i++) {
										sum += parseFloat(modelValue[i]) * multiplier;

										if (multiplier == 2) {
											multiplier = 7;
										} else {
											multiplier--;
										}
									}

									if (sum % 11 != 0) {
										verficationDigit = 11 - (sum % 11);
									}

									if (verficationDigit != parseFloat(modelValue.substring(9, 10))) {
										return false;
									}
								}

								// Institucones públicas
								if (parseFloat(modelValue.substring(2, 3)) == 6) {
									if (parseFloat(modelValue.substring(9, 13)) == "0000") {
										return false;
									}

									var verficationDigit = 0;
									var sum = 0;
									var multiplier = 3;

									for (let i = 0; i < 8; i++) {
										sum += parseFloat(modelValue[i]) * multiplier;

										if (multiplier == 2) {
											multiplier = 7;
										} else {
											multiplier--;
										}
									}

									if (sum % 11 != 0) {
										verficationDigit = 11 - (sum % 11);
									}

									if (verficationDigit != parseFloat(modelValue.substring(8, 9))) {
										return false;
									}
								}

								return true;
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