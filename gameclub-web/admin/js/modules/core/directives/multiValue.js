angular.module('Core').directive('multiValue', function() {
	return {
		restrict: 'A',
		templateUrl: 'js/modules/core/directives/multiValue.html',
		scope: {
			list: "=",
			property: "@?"
		},
		link: function($scope, element, attrs) {
			$scope.isOpened = false;

			$scope.toggle = function() {
				console.log("several: ", angular.element("#several_" + $scope.$id));
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
			}
		}
	};
});