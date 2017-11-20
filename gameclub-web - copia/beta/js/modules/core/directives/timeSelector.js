angular.module('Core').directive('timeSelector', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/timeSelector.html',
        required: 'ngModel',
		scope: {
            ngModel: '=',
            ngDisabled: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {

			if(attrs.disabled != null) {
				if(attrs.disabled === '') {
					$scope.disableSelectors = true;
				} else {
					$scope.disableSelectors = angular.copy(JSON.parse(attrs.disabled));
				}
			}
			$scope.$watch('ngDisabled', function(newValue, oldValue) {
				if(newValue != null) {
					if(newValue === '') {
						$scope.disableSelectors = true;
					} else {
						$scope.disableSelectors = angular.copy(newValue);
					}
				}
			});

			$scope.openClock = function() {
				setTimeout(function() {
					element.find('input').trigger('click')
				}, 0);
			}

		}
	};
});
