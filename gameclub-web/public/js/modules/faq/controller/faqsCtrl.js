angular.module("Faq").controller('FaqsCtrl', function($scope, categories, openRest, forEach) {
	categories.$promise.then(function(data) {
		$scope.categories = data;
	});

	$scope.categorySelected = function(category) {
		$scope.questionSelected = false;
		
		if (!category.selected) {
			forEach($scope.categories, function(cat) {
				if (cat.selected) {
					cat.selected = false;
					return 'break';
				}
			});
			category.selected = true;

			if (category.faqs == null) {
				$scope.isLoading = true;

				openRest("faq/getFaqsFromCategory/:categoryId", true).get({categoryId: category.id}, function(data) {
					category.faqs = data;
					$scope.currentCategory = category;
					$scope.isLoading = false;
				}, function(error) {
					$scope.isLoading = false;
				});
			} else {
				$scope.currentCategory = category;
			}
		} else {
			$scope.questionSelected = null;
		}
	}

	$scope.setQuestion = function(faq) {
		$scope.questionSelected = faq;
	}
});