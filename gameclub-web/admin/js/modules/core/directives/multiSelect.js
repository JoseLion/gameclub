angular.module("Core").directive('multiSelect', function(forEach) {
	return {
		restrict: 'E',
		require: 'ngModel',
		templateUrl: "js/modules/core/directives/multiSelect.html",
		scope: {
			ngModel: "=",
			list: "=",
			property: "@",
			matchTemplate: "@",
			choiceTemplate: "@",
			onSelect: "&?"
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.itemSelected = function($item) {
				ctrl.$setTouched();

				if ($scope.ngModel == null) {
					$scope.ngModel = [];
				}

				let obj = {};
				obj[$scope.property] = $item;
				$scope.ngModel.push(obj);
				ctrl.$setViewValue($scope.ngModel);

				if ($scope.onSelect != null) {
					$scope.onSelect();
				}
			}

			$scope.itemRemoved = function($item) {
				ctrl.$setTouched();

				forEach($scope.ngModel, function(value, i) {
					if ($item == value[$scope.property]) {
						$scope.ngModel.splice(i, 1);
						return 'break';
					}
				});
			}

			$scope.$watchCollection("ngModel", function(newValue, oldValue) {
				if (newValue != null) {
					if (newValue.length <= 0) {
						ctrl.$setViewValue(null);
					}
				}
			});
		}
	};
});