angular.module("ContactUs").controller('ContactUsCtrl', function($scope, blogsPreview, sweet, openRest, notif, forEach) {
	$scope.ContactUs = {};

	blogsPreview.$promise.then(function(data) {
		setPageBlogsMostSeen(data);
	});

	$scope.sendContactUs = function() {
		sweet.default("Nos enviará un correo con su mensaje e información", function() {
			openRest("publicUser/sendContactUs").post($scope.contactUs, function() {
				notif.success("El correo se envió con éxito");
				$scope.ContactUs = {};
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	$scope.$watch('currentPageMostSeen', function(newValue, oldValue) {
		if (newValue != null && newValue != oldValue) {
			openRest("levelapBlog/findArticles").post({isMostSeen: true, page: newValue}, function(data) {
				setPageBlogsMostSeen(data);
			});
		}
	});

	function setPageBlogsMostSeen(data) {
		$scope.blogsPreview = data.content;
		$scope.totalPagesMostSeen = data.totalPages;
	}
});