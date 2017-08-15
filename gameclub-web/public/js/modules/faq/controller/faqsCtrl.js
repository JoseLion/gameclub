angular.module("Faq").controller('FaqsCtrl', function($scope, categories, openRest) {
	categories.$promise.then(function(data) {
		$scope.categories = data;
	});

	$scope.categorySelected = function(category) {
		if (category.faqs == null) {
			openRest("faq/getFaqsFromCategory/:categoryId", true).get({categoryId: category.id}, function(data) {
				category.faqs = data;
			});
		}
	}
});