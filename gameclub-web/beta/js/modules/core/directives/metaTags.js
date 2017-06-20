angular.module("Core").directive('metaTags', function($rootScope, $state) {
	return {
		restrict: 'A',
		link: function($scope, element, attrs) {
			$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams, options) {
				for (let i = element.find("meta").length; i >= 0; i--) {
					let meta = angular.element(element.find("meta")[i]);
					
					if (meta.attr("property") == "og:title") {
						meta.remove();
					}
				}

				if ($state.current.data != null && $state.current.data.meta != null) {
					let metaTitle = angular.element('<meta property="og:title" content="' + $state.current.data.meta["og:title"] + '">');
					console.log("meta: ", metaTitle[0]);

					element.append(metaTitle);
				}
			});
		}
	};
});