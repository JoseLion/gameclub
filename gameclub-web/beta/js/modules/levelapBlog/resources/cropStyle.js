angular.module("LevelapBlog").directive('cropStyle', function() {
	return {
		restrict: 'A',
		link: function($scope, element, attrs) {
			element.on('load', function() {
				//console.log("element: ", element[0].naturalWidth, 'x', element[0].naturalHeight);
				let ratio = element[0].naturalHeight / element[0].naturalWidth;
				//let ratio = element[0].naturalWidth / element[0].naturalHeight;
				
				element.css('transform', 'translate(' + $scope.$eval(attrs.cropStyle).a + 'px,' + $scope.$eval(attrs.cropStyle).b + 'px)');
				//element.css('zoom', ($scope.$eval(attrs.cropStyle).c * 100 * ratio) + '%');
				element.css('zoom', ($scope.$eval(attrs.cropStyle).c * 100 * ratio) - ($scope.$eval(attrs.cropStyle).c * 2.75) + '%');
			});
		}
	};
});