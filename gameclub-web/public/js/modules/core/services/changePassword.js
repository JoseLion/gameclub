angular.module("Core").factory('changePassword', function($uibModal) {
	return function() {
		return $uibModal.open({
			size: 'md',
			backdrop: 'static',
			templateUrl: 'js/modules/core/modals/changePassword.html',
			controller: 'ChangePasswordCtrl',
			resolve: {
				loadPlugin: function($ocLazyLoad) {
					return $ocLazyLoad.load([{
						name: 'Core',
						files: ['js/modules/core/modals/changePasswordCtrl.js']
					}]);
				}
			}
		});
	}
});