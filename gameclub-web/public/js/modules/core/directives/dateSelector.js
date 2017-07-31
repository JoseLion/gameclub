angular.module('Core').directive('dateSelector', function($filter) {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/dateSelector.html',
		required: 'ngModel',
		scope: {
			ngModel: '=',
			ngDisabled: '=',
			showCalendar: '=?'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.showCalendar = $scope.showCalendar != null = $scope.showCalendar : true;
			$scope.dayFocused = false;
			$scope.monthFocused = false;
			$scope.yearFocused = false;
			$scope.calendarFocused = false;

			$scope.today = new Date();
			if($scope.ngModel == null) {
				$scope.finalDate = new Date();
			} else {
				$scope.ngModel = new Date($scope.ngModel);
				$scope.finalDate = new Date($scope.ngModel);
			}

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
			$scope.$watch('ngModel', function(newValue, oldValue) {
				if(newValue != null) {
					$scope.finalDate = newValue;
					$scope.day = $filter('date')($scope.ngModel, 'dd');
					$scope.month = $filter('date')($scope.ngModel, 'MM');
					$scope.year = $filter('date')($scope.ngModel, 'yyyy');
				} else if(oldValue != null && newValue == null){
					$scope.finalDate = new Date();
					$scope.day = null;
					$scope.month = null;
					$scope.year = null;
				}
			})

			$(document).bind('click', function(event){
				var isClickedElementChildOfPopup = element.find(event.target).length > 0;
				if (isClickedElementChildOfPopup) return;
				$scope.dayFocused = false;
				$scope.monthFocused = false;
				$scope.yearFocused = false;
				$scope.calendarFocused = false;
				if($scope.ngModel == null) {
					$scope.finalDate = new Date();
				} else {
					$scope.finalDate = new Date($scope.ngModel);
				}
				monthChanged = $scope.finalDate;
				$scope.isSelected = false;
				$scope.$apply();
			});

			$scope.isSelected = false;
			$scope.selection = function(type) {
				$scope.dayFocused = false;
				$scope.monthFocused = false;
				$scope.yearFocused = false;
				$scope.calendarFocused = false;
				if(!$scope.disableSelectors && type == 0) {
					$scope.numberSelector = [];
					$scope.dayFocused = true;
					for(let i=1 ; i<=getCalendarDays(Number($scope.month) - 1, Number($scope.year)) ; i++) {
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
					for(let i=(new Date().getFullYear()) ; i>=1950 ; i--) {
						$scope.numberSelector.push(i);
					}
				}
			};

			$scope.calendarSelected = function() {
				if(!$scope.disableSelectors) {
					$scope.dayFocused = false;
					$scope.monthFocused = false;
					$scope.yearFocused = false;
					$scope.calendarFocused = true;
					if(!$scope.isSelected) {
						$scope.calendar = [];
						$scope.calendarMonth = $filter('date')($scope.finalDate, 'MMMM');
						$scope.calendarYear = $filter('date')($scope.finalDate, 'yyyy');
						let totalDays = getCalendarDays($scope.finalDate.getMonth(), $scope.finalDate.getFullYear());
						for(let day=1 ; day<=totalDays ; ) {
							let week = [];
							for(let dayOfWeek=0 ; dayOfWeek<7 ; dayOfWeek++) {
								let date = new Date($scope.finalDate.getFullYear(), $scope.finalDate.getMonth(), day);
								if(date.getDay() == dayOfWeek) {
									week.push({
										date: date,
										active: $scope.ngModel == null ? false : $scope.ngModel.getDate() == date.getDate() && $scope.ngModel.getMonth() == date.getMonth() && $scope.ngModel.getFullYear() == date.getFullYear() ? true : false
									});
									if(++day > totalDays) {
										break;
									}
								} else {
									week.push('');
								}
							}
							$scope.calendar.push(week);
						}
					}
				}
			};

			let monthChanged = $scope.finalDate;
			$scope.changeMonth = function(direction) {
				let month = monthChanged.getMonth();
				if(direction == -1) month += month == 0 ? 11 : direction;
				else month += month == 11 ? -11 : direction;
				let year = monthChanged.getFullYear();
				if(direction == -1) year += month == 11 ? direction : 0;
				else year += month == 0 ? direction : 0;
				monthChanged = new Date(year, month, 1);

				let totalDays = getCalendarDays(month, year);
				$scope.calendar = [];
				$scope.calendarMonth = $filter('date')(new Date(year, month, 1), 'MMMM');
				$scope.calendarYear = $filter('date')(new Date(year, month, 1), 'yyyy');
				for(let day=1 ; day<=totalDays ; ) {
					let week = [];
					for(let dayOfWeek=0 ; dayOfWeek<7 ; dayOfWeek++) {
						let date = new Date(year, month, day);
						if(date.getDay() == dayOfWeek) {
							week.push({
								date: date,
								active: $scope.ngModel == null ? false : $scope.ngModel.getDate() == date.getDate() && $scope.ngModel.getMonth() == date.getMonth() && $scope.ngModel.getFullYear() == date.getFullYear() ? true : false
							});
							if(++day > totalDays) {
								break;
							}
						} else {
							week.push('');
						}
					}
					$scope.calendar.push(week);
				}
				$scope.finalDate = monthChanged;
			};

			$scope.daySelected = function(number) {
				$scope.day = number;
				$scope.dayFocused = false;
				let year = Number($scope.year);
				year = year == null || isNaN(year) ? $scope.finalDate.getFullYear() : year;
				let month = Number($scope.month);
				month = month == null || isNaN(month) ? $scope.finalDate.getMonth() : month - 1;
				let day = Number($scope.day);
				if(day > getCalendarDays(month, year)) {
					day = getCalendarDays(month, year)
				}
				$scope.ngModel = new Date(year, month, day);
			};

			$scope.monthSelected = function(number) {
				$scope.month = number;
				$scope.monthFocused = false;
				let year = Number($scope.year);
				year = year == null || isNaN(year) ? $scope.finalDate.getFullYear() : year;
				let month = Number($scope.month) - 1;
				let day = Number($scope.day);
				day = day == null || isNaN(day) ? $scope.finalDate.getDate() : day;
				if(day > getCalendarDays(month, year)) {
					day = getCalendarDays(month, year)
				}
				$scope.ngModel = new Date(year, month, day);
			};

			$scope.yearSelected = function(number) {
				$scope.year = number;
				$scope.yearFocused = false;
				let year = Number($scope.year);
				let month = Number($scope.month);
				month = month == null || isNaN(month) ? $scope.finalDate.getMonth() : month - 1;
				let day = Number($scope.day);
				day = day == null || isNaN(day) ? $scope.finalDate.getDate() : day;
				if(day > getCalendarDays(month, year)) {
					day = getCalendarDays(month, year)
				}
				$scope.ngModel = new Date(year, month, day);
			};

			let actualDate;
			$scope.dateSelected = function(obj) {
				if(actualDate != null) {
					actualDate.active = false;
				}
				obj.active = true;
				$scope.isSelected = true;
				actualDate = obj;
				$scope.ngModel = actualDate.date;
				$scope.calendarFocused = false;
			};

			function getCalendarDays(month, year) {
				if(year % 4 == 0 && month == 1) return 29;
				else if(month == 1) return 28;
				else if(month == 3 || month == 5 || month == 8 || month == 10) return 30;
				else return 31;
			}

		}
	};
});
