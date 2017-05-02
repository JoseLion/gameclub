angular.module('Core').directive('dateSelector', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/dateSelector.html',
		required: 'ngModel',
		scope: {
			ngModel: '=',
			ngDisabled: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {

			$scope.dayFocused = false;
			$scope.monthFocused = false;
			$scope.yearFocused = false;
			$scope.calendarFocused = false;

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

			$(document).bind('click', function(event){
				var isClickedElementChildOfPopup = element.find(event.target).length > 0;
				if (isClickedElementChildOfPopup) return;

				$scope.dayFocused = false;
				$scope.monthFocused = false;
				$scope.yearFocused = false;
				$scope.calendarFocused = false;
				$scope.$apply();

			});


			$scope.selection = function(type) {
				if(!$scope.disableSelectors && type == 0) {
					$scope.numberSelector = [];
					$scope.dayFocused = true;
					for(let i=1 ; i<=31 ; i++) {
						$scope.numberSelector.push(i < 10 ? '0'.concat(i) : i);
					}
				} else if(!$scope.disableSelectors && type == 1) {
					$scope.numberSelector = [];
					$scope.monthFocused = true;
					for(let i=1 ; i<=12 ; i++) {
						$scope.numberSelector.push(i < 10 ? '0'.concat(i) : i);
					}
				} else if(!$scope.disableSelectors && type == 2) {
					$scope.numberSelector = [];
					$scope.yearFocused = true;
					for(let i=(new Date().getFullYear()) ; i>=1980 ; i--) {
						$scope.numberSelector.push(i);
					}
				}
			};

			$scope.calendarSelected = function() {
				if(!$scope.disableSelectors) $scope.calendarFocused = true;
			}

			$scope.daySelected = function(number) {
				$scope.day = number;
				$scope.dayFocused = false;
				setTimeout(function() { $scope.$apply(); }, 0);
			};

			$scope.monthSelected = function(number) {
				$scope.month = number;
				$scope.monthFocused = false;
				setTimeout(function() { $scope.$apply(); }, 0);
			};

			$scope.yearSelected = function(number) {
				$scope.year = number;
				$scope.yearFocused = false;
				setTimeout(function() { $scope.$apply(); }, 0);
			};

		}
	};
});
