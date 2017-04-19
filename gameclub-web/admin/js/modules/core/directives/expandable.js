angular.module("Core").directive('expandable', function($compile) {
	return {
		restrict: 'A',
		scope: {
			expandable: "=?",
			template: "@",
			clickHandler: "@?",
			isCollapsed: "=?",
			rootTag: "@?"
		},
		link: function($scope, element, attrs) {
			setTimeout(function() {
				$scope.$apply(function() {
					$scope.rootTag = $scope.rootTag != null ? $scope.rootTag : 'ng-include';
					let handlerView = $scope.clickHandler != null ? angular.element("#" + $scope.clickHandler) : element;
					$scope.collapsed = $scope.collapsed != null ? $scope.collapsed : false;
					let includeType = $scope.rootTag != 'ng-include' ? "ng-include" : "src";


					$scope.expandableView = angular.element('<' + $scope.rootTag + ' id="expandable_view_{{$id}}" ' + includeType +'="template"></' + $scope.rootTag + '>');
					$scope.expandableView = $compile($scope.expandableView)($scope);
					$scope.expandableView.insertAfter(element);

					if ($scope.isCollapsed == null) {
						$scope.isCollapsed = false;
						toggleView(0);
					}

					handlerView.css("cursor", "pointer");
					handlerView.on("click", function(event) {
						toggleView();
					});
				});
			}, 100);

			function toggleView(time) {
				if (time == null) {
					time = 250;
				}

				setTimeout(function() {
					$scope.$apply(function() {
						let nextElement = angular.element($scope.expandableView[0].nextElementSibling);
						nextElement.toggle(time);
						$scope.isCollapsed = !$scope.isCollapsed;
					});
				}, 0);
			}
		}
	}
});