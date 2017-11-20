angular.module("Core").directive('radioButton', function($rootScope) {
	return {
		restrict: 'E',
		require: 'ngModel',
		scope: {
			ngModel: "=",
			ngValue: "=",
			ngChange: "&?",
			iconClass: '@?'
		},
		template: 	'<button type="button" class="btn btn-circle" ng-class="active ? activeClass : defaultClass" ng-click="toogleCheck()" style="width: 30px; height: 30px; transform: scale(0.8); border-width: 2px;" ng-disabled="isDisabled">' +
						'<i class="fa fa-lg" ng-class="iconClass" ng-if="active"></i>' +
					'</button>',
		link: function($scope, element, attrs, ctrl) {
			$scope.defaultClass = "btn-default";
			$scope.activeClass = "btn-primary";
			$scope.iconClass = $scope.iconClass != null ? $scope.iconClass : 'fa-check';
			$scope.active = false;
			var button = element.find("button");

			$scope.$watch("ngModel", function(newValue, oldValue) {
				if (newValue === $scope.ngValue) {
					$scope.active = true;
				} else {
					$scope.active = false;
				}

				if ($scope.ngChange != null) {
					$scope.ngChange();
				}
			});

			$scope.$watch(function() {return attrs.disabled;}, function(newValue, oldValue) {
				if (newValue == true || newValue === "") {
					$scope.isDisabled = true;
				} else {
					$scope.isDisabled = false;
				}
			});

			if (attrs.large != null) {
				button.addClass("btn-lg");
				button.css("width", "50px");
				button.css("height", "50px");
				button.css("transform", "scale(1.0)");
			}

			if (attrs.medium != null) {
				button.removeClass("btn-lg");
				button.css("width", "30px");
				button.css("height", "30px");
				button.css("transform", "scale(1.0)");
			}

			if (attrs.success != null) {
				$scope.activeClass = "btn-success";
			}

			if (attrs.warning != null) {
				$scope.activeClass = "btn-warning";
			}

			if (attrs.danger != null) {
				$scope.activeClass = "btn-danger";
			}

			if (attrs.info != null) {
				$scope.activeClass = "btn-info";
			}

			$scope.toogleCheck = function() {
				if ($scope.active == false) {
					clearAll();
					ctrl.$setViewValue($scope.ngValue);
					$scope.active = true;
				}
			}

			$scope.$on("clearMe", function(event, myself) {
				if (element[0] == myself[0]) {
					$scope.active = false;
				}
			});

			function clearAll() {
				let radios = [];
				let query = document.getElementsByTagName("radio-button");

				for (let i = 0; i < query.length; i++) {
					let radioBtn = angular.element(query[i]);

					if (attrs.name != null) {
						if (radioBtn.attr("name") == attrs.name) {
							radios.push(radioBtn);
						}
					} else {
						if (radioBtn.attr("name") == null) {
							radios.push(radioBtn);
						}
					}
				}

				for (let i = 0; i < radios.length; i++) {
					$rootScope.$broadcast("clearMe", radios[i]);
				}
			}
		}
	};
});