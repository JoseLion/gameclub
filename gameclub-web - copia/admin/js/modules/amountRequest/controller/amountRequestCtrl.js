angular.module("AmountRequest").controller('AmountRequestCtrl',function($scope, amountRequestCatalog, amountRequests, getDTOptions, $state, friendlyUrl, rest, Const, $uibModal){

	$scope.search = {};
	$scope.amountRequests = {};

	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	amountRequests.$promise.then(function(data){
		$scope.amountRequests = data;
	});

	amountRequestCatalog.$promise.then(function(data) {
		$scope.amountRequestCatalog = data;
	});

	$scope.find = function() {
		if ($scope.search.catalog != null) {
			$scope.search.catalogId = $scope.search.catalog.id;
		}
		rest("amountRequest/findAmountRequest", true).post($scope.search, function(data) {
			$scope.amountRequests = data;
		});
	}

	$scope.clean = function() {
		$scope.search = {};
		rest("amountRequest/findAll", true).post(function(data) {
			$scope.amountRequests = data;
		});
	}

	$scope.viewAmountRequest = function(amountRequest) {
		$uibModal.open({
			size: 'md',
			backdrop: true,
			templateUrl: 'js/modules/amountRequest/view/amountRequestModal.html',
			controller: 'AmountRequestModalCtrl',
			resolve: {
				loadPlugin: function($ocLazyLoad) {
					return $ocLazyLoad.load([{
						name: 'AmountRequest',
						files: ['js/modules/amountRequest/controller/amountRequestModalCtrl.js']
					}]);
				},
				amountRequest: function(rest) {
					return rest("amountRequest/findOne/:id").get({id: amountRequest.id}, function(data) {
						return data;
					});
				},
				amountRequestCatalog: function(rest, Const) {
					return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.amountRequest}, function(data) {
						return data;
					});
				}
			}
		}).
		result.then(function(data) {
			let index = $scope.amountRequests.indexOf(amountRequest);
			$scope.amountRequests[index] = data;
		});

	}

	$scope.setMountRequest = function(kit) {
		$uibModal.open(getTrackingModal(null, null, kit)).result.then(function(data) {
			let index = $scope.welcomeKits.indexOf(kit);
			$scope.welcomeKits[index] = data;
		});
	}

	let getTrackingModal = function(loan, restore, kit) {
		return {
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

				loan: function(rest) {
					if (loan != null) {
						return rest("loan/findOne/:id").get({id: loan.id}, function(data) {
							return data;
						});
					} else {
						return null;
					}
				},

				restore: function(rest) {
					if (restore != null) {
						return rest("restore/findOne/:id").get({id: restore.id}, function(data) {
							return data;
						});
					} else {
						return null;
					}
				},

				kit: function(rest) {
					if (kit != null) {
						return rest("welcomeKit/findOne/:id").get({id: kit.id}, function(data) {
							return data;
						});
					} else {
						return null;
					}
				},

				shippingCatalog: function(rest, Const) {
					return rest("catalog/findChildrenOf/:code", true).get({code: Const.code.shippingCatalog}, function(data) {
						return data;
					});
				}
			}
		};
	}
});