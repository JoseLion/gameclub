angular.module("Core").directive('archive', function(openRest, getImageBase64) {
	return {
		restrict: 'A',
		scope: {
			archive: "=",
			placeholder: "@?",
			ngModel: "=?"
		},
		link: function($scope, element, attrs) {
			let archiveLoaded = false;
			if ($scope.placeholder != null) {
				attrs.$set('src', $scope.placeholder);
			}

			setImageSource();

			$scope.$watch("archive", function(newValue, oldValue) {
				if (newValue !== oldValue) {
					setImageSource();
				}
			}, true);

			$scope.$watch('placeholder', function(newValue, oldValue) {
				if (newValue != null && !archiveLoaded) {
					attrs.$set('src', newValue);
				}
			});

			function setImageSource() {
				if ($scope.archive != null) {
					openRest("archive/downloadFile").download({name: $scope.archive.name, module: $scope.archive.module}, function(data) {
						if (element[0].tagName == "image") {
							attrs.$set('xlink:href', getImageBase64(data, $scope.archive.type));
						} else {
							attrs.$set('src', getImageBase64(data, $scope.archive.type));
							attrs.$set('alt', $scope.archive.name);
							attrs.$set('title', $scope.archive.title);
							archiveLoaded = true;
						}

						$scope.ngModel = getImageBase64(data, $scope.archive.type);
					});
				}
			}
		}
	};
});