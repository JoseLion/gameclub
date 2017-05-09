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

			let currentList = "";
			let itemList = '<li>#number</li>';

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

			// $scope.selection = function(element) {
			// 	let input = angular.element(element);
			// 	let parent = input.parent();
			// 	currentList = '<ul class="dropdown">';
			// 	for(let i=1 ; i<=31 ; i++) {
			// 		currentList = currentList.concat(itemList.replace('#number', i));
			// 	}
			// 	currentList = currentList.concat('</ul>');
			//
			// 	angular.element(input).parent().append(currentList);
			// };
		}
	};
});
