angular.module("LevelapBlogAdmin").controller('ViewArticlesCtrl', function($scope, articles, categories, tags, getDTOptions, BlogConst, $state, friendlyUrl, rest, sweet) {
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

	$scope.find = function() {
		rest("levelapBlog/findArticles").post($scope.search, function(data) {
			setPagedData(data);
		});
	}

	$scope.clear = function() {
		$scope.search = {};
		$scope.find();
	}

	$scope.pageChanged = function() {
		$scope.search.page = $scope.currentPage - 1;
		$scope.find();
	}

	$scope.addArticle = function() {
		$state.go("^.addArticle");
	}

	$scope.editArticle = function(article) {
		$state.go("^.editArticle", {id: article.id, title: friendlyUrl(article.title)});
	}

	$scope.manageComments = function(article) {
		$state.go("^.manageComments", {id: article.id, title: friendlyUrl(article.title)});
	}

	$scope.changeStatus = function(article) {
		sweet.changeStatus(function() {
			rest("levelapBlog/changeArticleStatus/:id").get({id: article.id}, function(data) {
				article.status = data;
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	function setPagedData(data) {
		$scope.articles = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (BlogConst.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});