angular.module("Core").directive('imageRequired', function() {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function($scope, element, attrs, ctrl) {
			let isRequired = true;

			$scope.getAttr = function() {
				return {
					required: attrs.imageRequired
				};
			}

			$scope.$watch($scope.getAttr, function(newValue, oldValue) {
				if (newValue.imageRequired == null || newValue.imageRequired == "") {
					isRequired = true;
				} else {
					if ($scope.$eval(newValue.imageRequired) == true) {
						isRequired = true;
					} else {
						isRequired = false;
					}
				}
				
				ctrl.$validate();
			}, true);

			ctrl.$validators.imageRequired = function(modelValue, viewValue) {
				if (isRequired) {
					if (modelValue != null) {
						if (modelValue != '//:0') {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		}
	};
});