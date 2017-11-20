angular.module("Faq").controller('ManageFaqCtrl', function($scope, faq, categories, rest, sweet, $state) {
	$scope.faq = {};

	if (faq != null) {
		faq.$promise.then(function(data) {
			$scope.faq = data;
		});
	}

	categories.$promise.then(function(data) {
		$scope.categories = data;
	});

	$scope.save = function() {
		sweet.save(function() {
			rest("faq/save").post($scope.faq, function() {
				sweet.success();
				$state.go("^.viewFaqs");
				sweet.close();
			}, function(error) {
				sweet.error(error.data != null ? error.data.message : error);
			});
		});
	}

	$scope.cancel = function() {
		$state.go("^.viewFaqs");
	}
});