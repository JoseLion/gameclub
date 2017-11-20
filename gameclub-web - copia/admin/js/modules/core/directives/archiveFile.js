angular.module("Core").directive('archiveFile', function(rest, getImageBase64) {
	return {
		restrict: 'A',
		scope: {
			archiveFile: "=",
			placeholder: "@?",
			ngModel: '=?'
		},
		link: function($scope, element, attrs) {
			if ($scope.placeholder != null) {
				attrs.$set('src', $scope.placeholder);
			}

			setImageSource();

			$scope.$watch("archiveFile", function(newValue, oldValue) {
				if (newValue !== oldValue) {
					setImageSource();
				}
			}, true);

			function setImageSource() {
				if ($scope.archiveFile != null) {
					rest("archive/downloadFile").download({name: $scope.archiveFile.name, module: $scope.archiveFile.module}, function(data) {
						attrs.$set('src', getImageBase64(data, $scope.archiveFile.type));
						attrs.$set('alt', $scope.archiveFile.name);
						attrs.$set('title', $scope.archiveFile.title);

						$scope.ngModel = getImageBase64(data, $scope.archiveFile.type);
					});
				}
			}
		}
	};
});
