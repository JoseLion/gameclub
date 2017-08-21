angular.module("Logistic").controller('ViewLogisticsCtrl', function($scope, welcomeKits, shippingCatalog, getDTOptions, Const, $uibModal) {
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

	shippingCatalog.$promise.then(function(data) {
		$scope.shippingCatalog = data;
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

	$scope.viewWelcomeKit = function(kit) {
		$uibModal.open({
			size: 'md',
			backdrop: true,
			templateUrl: 'js/modules/logistic/view/welcomeKitModal.html',
			controller: 'WelcomeKitModalCtrl',
			resolve: {
				loadPlugin: function($ocLazyLoad) {
					return $ocLazyLoad.load([{
						name: 'Logistic',
						files: ['js/modules/logistic/controller/welcomeKitModalCtrl.js']
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

	$scope.setTracking = function(kit) {
		let modal = $uibModal.open({
			size: 'md',
			backdrop: 'static',
			templateUrl: 'js/modules/logistic/view/trackingModal.html',
			controller: "TrackingModalCtrl",
			resolve: {
				loadPlugin: function($ocLazyLoad) {
					return $ocLazyLoad.load([{
						name: 'Logistic',
						files: ['js/modules/logistic/controller/trackingModalCtrl.js']
					}]);
				},

				kit: function(rest) {
					return rest("welcomeKit/findOne/:id").get({id: kit.id}, function(data) {
						return data;
					});
				},

				shippingCatalog: function(rest, Const) {
					return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.shippingCatalog}, function(data) {
						return data;
					});
				}
			}
		});

		modal.result.then(function(data) {
			let index = $scope.welcomeKits.indexOf(kit);
			$scope.welcomeKits[index] = data;
		});
	}

	function setPagedWelcomeKits(data) {
		$scope.welcomeKits = data.content;
		$scope.totalElementsW = data.totalElements;
		$scope.beginningW = (Const.tableSize * data.number) + 1;
		$scope.endW = $scope.beginningW + data.numberOfElements - 1;
		$scope.totalPagesW = data.totalPages;
	}
});