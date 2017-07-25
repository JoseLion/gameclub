angular.module("Faq").controller('ViewFaqsCtrl', function($scope, faqs, categories, getDTOptions, rest, $state, friendlyUrl, sweet) {
	$scope.search = {};
	$scope.dtOptions = getDTOptions.unpaged();
	$scope.dtColumnDefs = getDTOptions.notSortableAll(5);

	faqs.$promise.then(function(data) {
		$scope.faqs = data;
	});

	categories.$promise.then(function(data) {
		$scope.categories = data;
	});

	$scope.find = function() {
		rest("faq/findFaqs", true).post($scope.search, function(data) {
			$scope.faqs = data;
		});
	}

	$scope.clear = function() {
		$scope.search = {};
		$scope.find();
	}

	$scope.addFaq = function() {
		$state.go("^.addFaq");
	}

	$scope.editFaq = function(faq) {
		$state.go("^.editFaq", {id: faq.id, question: friendlyUrl(faq.question)});
	}

	$scope.changeStatus = function(faq) {
		sweet.changeStatus(function() {
			rest("faq/changeStatus/:id").get({id: faq.id}, function(data) {
				faq.status = data;
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}
});