angular.module("LevelapBlogAdmin").controller('ViewArticlesCtrl', function($scope, articles, categories, tags, getDTOptions, BlogConst) {
	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	setTimeout(function() {
		$scope.$apply(function() {
			$scope.dtColumnDefs = getDTOptions.notSortableAll(angular.element("#articles-table")[0].rows[0].cells.length);
		});
	}, 100);

	if (categories != null) {
		categories.$promise.then(function(data) {
			$scope.categories = data;
		});
	}

	if (tags != null) {
		tags.$promise.then(function(data) {
			$scope.tags = data;
		});
	}

	articles.$promise.then(function(data) {
		setPagedData(data);
	});

	function setPagedData(data) {
		$scope.articles = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (BlogConst.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});