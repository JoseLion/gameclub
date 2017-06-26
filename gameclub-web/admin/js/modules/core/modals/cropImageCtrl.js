angular.module("Core").controller('CropImageCtrl', function($scope, $uibModalInstance, base64Image, shape) {
	$scope.crop = {};
	$scope.crop.base64Image = base64Image;
	$scope.shape = shape;

	$scope.ok = function() {
		$uibModalInstance.close($scope.crop.croppedImage);
	}

	$scope.cancel = function() {
		$uibModalInstance.dismiss();
	}
});