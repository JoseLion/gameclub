angular.module("Core").directive('archive', function(openRest, getImageBase64) {
	return {
		restrict: 'A',
		scope: {
			archive: "=",
			placeholder: "@?"
		},
		link: function($scope, element, attrs) {
			if ($scope.placeholder != null) {
				attrs.$set('src', $scope.placeholder);
			}

			setImageSource();

			$scope.$watch("archive", function(newValue, oldValue) {
				if (newValue !== oldValue) {
					setImageSource();
				}
			}, true);

			function setImageSource() {
				if ($scope.archive != null) {
					openRest("archive/downloadFile").download({name: $scope.archive.name, module: $scope.archive.module}, function(data) {
						attrs.$set('src', getImageBase64(data, $scope.archive.type));
						attrs.$set('alt', $scope.archive.name);
						attrs.$set('title', $scope.archive.title);
					});
				}
			}
		}
	};
});
