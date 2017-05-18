angular.module('LevelapBlog').directive('articlePreview', function() {
	let baseSrc;
    for (let i = document.getElementsByTagName("script").length - 1; i >= 0; i--) {
        let script = angular.element(document.getElementsByTagName("script")[i]);
        if (script.attr("src") != null && script.attr("src").indexOf("levelapBlog.js") > -1) {
            baseSrc = script.attr("src").substring(0, script.attr("src").indexOf("levelapBlog.js"));
            break;
        }
    }
	return {
		restrict: 'E',
		templateUrl: baseSrc.concat('resources/articlePreview.html'),
		required: 'ngModel',
		scope: {
			ngModel: '=',
			isComplete: '@',
			comments: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.showAllInfo = false;
			if($scope.isComplete != null) {
				if($scope.isComplete == '') {
					$scope.showAllInfo = true
				} else {
					$scope.showAllInfo = JSON.parse($scope.isComplete);
				}
			}
		}
	};
});
