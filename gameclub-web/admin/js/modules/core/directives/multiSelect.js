angular.module("Core").directive('multiSelect', function(forEach, getIndexOfArray) {
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
			onSelect: "&?",
			uniqueKey: "@?"
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			if ($scope.uniqueKey == null) {
				$scope.uniqueKey = "id";
			}

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
					if ($item[$scope.uniqueKey] == value[$scope.property][$scope.uniqueKey]) {
						$scope.ngModel.splice(i, 1);
						return 'break';
					}
				});
			}

			$scope.$watchCollection("ngModel", function(newValue, oldValue) {
				if (newValue != null) {
					if (newValue.length <= 0) {
						ctrl.$setViewValue(null);
					} else {
						$scope.selected = [];

						forEach(newValue, function(value) {
							let index = -1;

							if ($scope.property != null) {
								index = getIndexOfArray($scope.list, $scope.uniqueKey, value[$scope.property][$scope.uniqueKey]);
							} else {
								index = getIndexOfArray($scope.list, $scope.uniqueKey, value[$scope.uniqueKey]);
							}

							if (index > -1) {
								$scope.selected.push($scope.list[index]);
							}
						});
					}
				}
			});
		}
	};
});