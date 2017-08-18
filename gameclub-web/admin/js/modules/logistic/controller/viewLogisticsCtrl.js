angular.module("Logistic").controller('ViewLogisticsCtrl', function($scope, welcomeKits, getDTOptions, Const) {
	$scope.searchW = {};
	$scope.totalElementsW;
	$scope.beginningW;
	$scope.endW;

	$scope.dtOptionsW = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElementsW, $scope.beginningW, $scope.endW);
	});

	$scope.dtColumnDefsW = getDTOptions.notSortableAll(6);

	welcomeKits.$promise.then(function(data) {
		setPagedWelcomeKits(data);
	});

	$scope.findWelcomeKits = function() {
		rest("game/findGames").post($scope.search, function(data) {
			setPagedWelcomeKits(data);
		});
	}

	$scope.clearWelcomeKits = function() {
		$scope.searchW = {};
		$scope.findWelcomeKits();
	}

	$scope.welcomeKitsPageChanged = function() {
		$scope.searchW.page = $scope.currentPageW - 1;
		$scope.findWelcomeKits();
	}

	function setPagedWelcomeKits(data) {
		$scope.welcomeKits = data.content;
		$scope.totalElementsW = data.totalElements;
		$scope.beginningW = (Const.tableSize * data.number) + 1;
		$scope.endW = $scope.beginningW + data.numberOfElements - 1;
		$scope.totalPagesW = data.totalPages;
	}
});