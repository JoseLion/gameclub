angular.module("Core").directive('checkbox', function() {
	return {
		restrict: 'E',
		require: 'ngModel',
		scope: {
			ngModel: "=",
			ngChange: "&?",
			ngTrue: "=?",
			ngFalse: "=?"
		},
		template: 	'<button type="button" class="btn btn-xs" ng-class="active ? activeClass : defaultClass" style="width: 24px; height: 24px; border-width: 2px;" ng-disabled="isDisabled">' +
						'<i class="fa fa-check" ng-if="active"></i>' +
					'</button>',
		link: function($scope, element, attrs, ctrl) {
			$scope.defaultClass = "btn-default";
			$scope.activeClass = "btn-primary";
			$scope.active = false;
			$scope.ngTrue = $scope.ngTrue != null ? $scope.ngTrue : true;
			$scope.ngFalse = $scope.ngFalse != null ? $scope.ngFalse : false;
			var button = element.find("button");

			if (ctrl.$modelValue == null) {
				ctrl.$setViewValue($scope.ngFalse);
			}
			
			$scope.$watch("ngModel", function(newValue, oldValue) {
				if (newValue === $scope.ngTrue) {
					$scope.active = true;
				} else {
					$scope.active = false;
				}
			}, true);

			$scope.$watch(function() {return attrs.disabled;}, function(newValue, oldValue) {
				if (newValue == true || newValue === "") {
					$scope.isDisabled = true;
				} else {
					$scope.isDisabled = false;
				}
			});

			if (attrs.medium != null) {
				removeBtnClass();
				button.addClass("btn-sm");
				button.css("width", "34px");
				button.css("height", "34px");
			}

			if (attrs.large != null) {
				removeBtnClass();
				button.css("width", "40px");
				button.css("height", "40px");
			}

			if (attrs.big != null) {
				removeBtnClass();
				button.addClass("btn-lg");
				button.css("width", "52px");
				button.css("height", "52px");
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

			element.on('click', function(event) {
				event.preventDefault();
				
				if (ctrl.$modelValue === $scope.ngTrue) {
					ctrl.$setViewValue($scope.ngFalse);
					$scope.active = false;
				} else {
					ctrl.$setViewValue($scope.ngTrue);
					$scope.active = true;
				}
			});

			function removeBtnClass() {
				button.removeClass("btn-xs");
				button.removeClass("btn-sm");
				button.removeClass("btn-lg");
			}
		}
	};
});