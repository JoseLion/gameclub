angular.module("Core").factory('copyLink', function($uibModal) {
	return function() {
		return $uibModal.open({
            size: 'md',
            backdrop: 'static',
            templateUrl: 'js/modules/core/modals/copyLink.html',
            controller: "CopyLinkCtrl",
            resolve: {
                loadPlugin: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
						name: 'Core',
						files: ['js/modules/core/modals/copyLinkCtrl.js']
					}]);
                }
            }
        });
	}
});