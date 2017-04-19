angular.module("Core").directive('blogPreview', function() {
	return {
		restrict: 'E',
		templateUrl: "js/modules/core/directives/blogPreview.html",
		scope: {
            blogId: '=',
            subtitle: '=',
            desc: '=',
            imgSrc: '=',
            date: '=',
            url: '='
		},
		replace: true,
		link: function($scope, element, attrs, ctrl) {
			$scope.completeBlog = function() {
				console.log('REDIRIGIR A LA PANTALLA DE BLOG COMPLETA Y PASAR EL ID PARA BUSCAR');
			}

		}
	};
});
