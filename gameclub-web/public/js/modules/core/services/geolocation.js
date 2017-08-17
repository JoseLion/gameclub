angular.module("Core").factory('geolocation', function($uibModal) {
	return function() {
		return $uibModal.open({
            size: 'md',
            backdrop: 'static',
            templateUrl: 'js/modules/core/modals/geolocation.html',
            controller: "GeolocationCtrl",
            resolve: {
                loadPlugin: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
						name: 'Core',
						files: ['js/modules/core/modals/geolocationCtrl.js']
					}, {
                        name: 'ngMap',
                        files: ['js/core/plugins/ng-map/ng-map.min.js']
                    }]);
                }
            }
        });
	}
});