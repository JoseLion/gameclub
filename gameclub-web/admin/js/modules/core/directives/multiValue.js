angular.module('Core').directive('multiValue', function() {
	return {
		restrict: 'A',
		templateurl: 'js/modules/core/directives/multiValue.html',
		scope: {
			list: "=",
			property: "@?"
		},
		controller: function($scope) {
			$scope.isOpened = false;

			$scope.toggle = function() {
				angular.element("#several_" + $scope.$id).slideToggle(200);
				$scope.isOpened = !$scope.isOpened;
			}

			$scope.setValue = function(value) {
				if ($scope.property != null) {
					$scope.myValue = value;
					return $scope.$eval("myValue." + $scope.property);
				} else {
					return value;
				}

				/*if ($scope.property != null) {
					if ($scope.condition == null) {
						if ($scope.secondary != null) {
							return value[$scope.property][$scope.secondary];
						} else {
							return value[$scope.property];
						}
					} else {
						if ($scope.secondary != null) {
							if (value[$scope.property][$scope.condition]) {
								return value[$scope.property][$scope.secondary];
							} else {
								return value[$scope.property][$scope.elseProperty];
							}
						} else {
							if (value[$scope.condition]) {
								return value[$scope.property];
							} else {
								return value[$scope.elseProperty];
							}
						}
					}
				} else {
					return value;
				}*/
			}
		}
	};
});