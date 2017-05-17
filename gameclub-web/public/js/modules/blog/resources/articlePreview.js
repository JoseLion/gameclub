angular.module('Blog').directive('articlePreview', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/blog/resources/articlePreview.html',
		required: 'ngModel',
		scope: {
			ngModel: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
		}
	};
});
