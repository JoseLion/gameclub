angular.module("WelcomeKit").controller('ViewWelcomeKitsCtrl', function($scope, welcomeKits, shippingCatalog, locations, getDTOptions, Const, rest, $uibModal) {
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

	$scope.viewWelcomeKit = function(kit) {
		$uibModal.open({
			size: 'md',
			backdrop: true,
			templateUrl: "js/modules/welcomeKit/view/detailsWelcomeKit.html",
			controller: "DetailsWelcomeKitCtrl",
			resolve: {
				loadPlugin: function($ocLazyLoad) {
					return $ocLazyLoad.load([{
						name: 'WelcomeKit',
						files: ['js/modules/welcomeKit/controller/detailsWelcomeKitCtrl.js']
					}]);
				},

				kit: function(rest) {
					return rest("welcomeKit/findOne/:id").get({id: kit.id}, function(data) {
						return data;
					});
				}
			}
		});
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