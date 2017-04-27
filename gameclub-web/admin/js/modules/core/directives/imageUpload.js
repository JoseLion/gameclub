angular.module("Core").directive('imageUpload', function(getImageBase64, rest) {
	return {
		restrict: 'E',
		require: 'ngModel',
		templateUrl: "js/modules/core/directives/imageUpload.html",
		scope: {
			ngModel: '=',
			placeholder: '@?',
			archive: '=?',
			ngChange: '&?'
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.imageInput = {};
			$scope.placeholder = $scope.placeholder != null ? $scope.placeholder : 'img/picture-holder.svg';
			$scope.isDisabled = ('disabled' in attrs) ? true : false;
			$scope.isRequired = ('required' in attrs) ? true : false;

			$scope.$watch('ngModel', function(newValue, oldValue) {
				ctrl.$setDirty();

				if (newValue != null && Object.prototype.toString.call(newValue) === '[object File]') {
					$scope.isLoading = true;

					let reader = new FileReader();
					reader.readAsArrayBuffer(newValue);
					reader.onload = function() {
						setTimeout(function() {
							$scope.$apply(function() {
								$scope.imageBase64 = getImageBase64(reader.result, newValue.type);
								$scope.isLoading = false;
							});
						}, 0);
					}
				}
			});

			$scope.$watch("archive", function(newValue, oldValue) {
				if (newValue != null) {
					$scope.isLoading = true;

					rest("archive/downloadFile").download({name: newValue.name, module: newValue.module}, function(data) {
						$scope.ngModel = new File([data], newValue.name, {type: newValue.type});
						$scope.isLoading = false;
					}, function(error) {
						$scope.isLoading = false;
					});
				}
			});

			$scope.$watch("imageInput.file", function(newValue, oldValue) {
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

			$scope.$watch(function() {return attrs.required;}, function(newValue, oldValue) {
				if (newValue == true || newValue === "") {
					$scope.isRequired = true;
				} else {
					$scope.isRequired = false;
				}
			});

			$scope.addImage = function() {
				if (!$scope.isLoading && !$scope.isDisabled) {
					ctrl.$setTouched();
					let imageInputId = "#image-input-element-" + $scope.$id;
					angular.element(imageInputId).trigger('click');
				}
			}

			$scope.removeImage = function() {
				ctrl.$setTouched();
				$scope.imageInput = {};
				ctrl.$setViewValue(null);
				delete $scope.imageBase64;
			}

			$scope.downloadImage = function() {
				let imageUrl = URL.createObjectURL($scope.ngModel);
				let anchor = document.createElement("a");
				anchor.download = $scope.ngModel.name;
				anchor.href = imageUrl;
				document.body.appendChild(anchor)
				anchor.click();
				delete anchor;
			}

			$scope.isNgModelFile = function() {
				if ($scope.ngModel == null) {
					return false;
				} else {
					if (Object.prototype.toString.call($scope.ngModel) === '[object File]') {
						return true;
					} else {
						return false;
					}
				}
			}
		}
	};
});
