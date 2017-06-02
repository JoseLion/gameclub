angular.module("Core").directive('fileUpload', function(rest){
	return {
		restrict: 'E',
		require: 'ngModel',
		scope: {
			ngModel: '=',
			archive: '=?',
			ngChange: '&?'
		},
		templateUrl: "js/modules/core/directives/fileUpload.html",
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.fileInput = {};
			$scope.isDisabled = ('disabled' in attrs) ? true : false;

			$scope.$watch("ngModel", function(newValue, oldValue) {
				ctrl.$setDirty();
			});

			$scope.$watch("archive", function(newValue, oldValue) {
				if (newValue != null) {
					$scope.isLoading = true;

					rest("archive/downloadFile").download(newValue.path, function(data) {
						$scope.ngModel = new File([data], newValue.name, {type: newValue.type});
						$scope.isLoading = false;
					}, function(error) {
						$scope.isLoading = false;
					});
				}
			});

			$scope.$watch("fileInput.file", function(newValue, oldValue) {
				if (newValue != null) {
					ctrl.$setViewValue(newValue);
				}
			});

			$scope.$watch(function() {return attrs.disabled;}, function(newValue, oldValue) {
				if (newValue == true || newValue === "") {
					$scope.isDisabled = true;
				} else {
					$scope.isDisabled = false;
				}
			});

			$scope.addFile = function() {
				ctrl.$setTouched();
				let fileInputId = "#file-input-element-" + $scope.$id;
				angular.element(fileInputId).trigger('click');
			}

			$scope.removeFile = function() {
				ctrl.$setTouched();
				$scope.fileInput = {};
				ctrl.$setViewValue(null);
				delete $scope.ngModel;
			}

			$scope.downloadFile = function() {
				let fileUrl = URL.createObjectURL($scope.ngModel);
				let anchor = document.createElement("a");
				anchor.download = $scope.ngModel.name;
				anchor.href = fileUrl;
				document.body.appendChild(anchor)
				anchor.click();
				delete anchor;
			}

			$scope.hasFile = function() {
				if ($scope.ngModel != null && Object.prototype.toString.call($scope.ngModel) === '[object File]') {
					return true;
				}

				return false;
			}
		}
	};
});
