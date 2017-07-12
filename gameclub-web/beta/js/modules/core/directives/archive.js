angular.module("Core").directive('archive', function(openRest, getImageBase64) {
	return {
		restrict: 'A',
		scope: {
			archive: "=",
			placeholder: "@?",
			ngModel: "=?"
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
						let base64 = getImageBase64(data, $scope.archive.type);
						console.log("base64: ", base64.substring(0, 30));

						if (element[0].tagName == "image") {
							attrs.$set('xlink:href', base64);
						} else {
							attrs.$set('src', base64);
							attrs.$set('alt', $scope.archive.name);
							attrs.$set('title', $scope.archive.title);
						}

						$scope.ngModel = base64;
					});
				}
			}
		}
	};
});
