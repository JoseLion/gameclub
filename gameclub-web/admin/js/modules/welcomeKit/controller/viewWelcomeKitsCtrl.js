angular.module("WelcomeKit").controller('ViewWelcomeKitsCtrl', function($scope, welcomeKits, shippingCatalog, locations, getDTOptions, Const, rest) {
	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	$scope.dtColumnDefs = getDTOptions.notSortableAll(5);

	welcomeKits.$promise.then(function(data) {
		setPagedData(data);
	});

	shippingCatalog.$promise.then(function(data) {
		$scope.shippingCatalog = data;
	});

	locations.$promise.then(function(data) {
		$scope.provinces = data;
	});

	$scope.find = function() {
		rest("welcomeKit/findWelcomeKits").post($scope.search, function(data) {
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

	$scope.$watch("search.province", function(newValue, oldValue) {
		if (newValue != oldValue) {
			delete $scope.search.city;
		}
	});

	function setPagedData(data) {
		$scope.welcomeKits = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});