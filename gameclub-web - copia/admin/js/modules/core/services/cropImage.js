angular.module("Core").factory('cropImage', function($uibModal) {
	return function(base64Image, shape) {
		return $uibModal.open({
			size: 'md',
			backdrop: 'static',
			templateUrl: 'js/modules/core/modals/cropImage.html',
			controller: "CropImageCtrl",
			resolve: {
				loadPlugin: function($ocLazyLoad) {
					return $ocLazyLoad.load([{
						name: 'Core',
						files: ['js/modules/core/modals/cropImageCtrl.js']
					}, {
						name: 'angular-img-cropper',
						files: ['js/plugins/angular-img-cropper/angular-img-cropper.js']
					}]);
				},

				base64Image: function() {
					return base64Image;
				},

				shape: function() {
					return shape;
				}
			}
		});
	}
});