angular.module("LevelapBlogAdmin").controller('ViewArticlesCtrl', function($scope, articles, categories, tags, getDTOptions, BlogConst, $state, friendlyUrl) {
	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	let columns = 5;

	if (BlogConst.config.hasCategories) {
		columns++;
	}

	if (BlogConst.config.hasTags) {
		columns++;
	}

	$scope.dtColumnDefs = getDTOptions.notSortableAll(columns);

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

	$scope.addArticle = function() {
		$state.go("^.addArticle");
	}

	$scope.editArticle = function(article) {
		$state.go("^.editArticle", {id: article.id, title: friendlyUrl(article.title)});
	}

	function setPagedData(data) {
		$scope.articles = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (BlogConst.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});