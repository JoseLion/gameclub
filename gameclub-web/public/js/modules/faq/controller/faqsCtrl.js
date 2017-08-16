angular.module("Faq").controller('FaqsCtrl', function($scope, categories, openRest, forEach) {
	categories.$promise.then(function(data) {
		$scope.categories = data;
	});

	$scope.categorySelected = function(category) {
		if (!category.selected) {
			forEach($scope.categories, function(cat) {
				if (cat.selected) {
					cat.selected = false;
					return 'break';
				}
			});
			category.selected = true;

			if (category.faqs == null) {
				category.isLoading = true;

				openRest("faq/getFaqsFromCategory/:categoryId", true).get({categoryId: category.id}, function(data) {
					category.faqs = data;
					$scope.currentCategory = category;
					category.isLoading = false;
				}, function(error) {
					category.isLoading = false;
				});
			} else {
				$scope.currentCategory = category;
			}
		}
	}
});