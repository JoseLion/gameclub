angular.module("Logistic").controller('ViewLogisticsCtrl', function($scope, loans, restores, welcomeKits, shippingCatalog, provinces, getDTOptions, Const, $uibModal, rest) {
	$scope.searchL = {};
	$scope.totalElementsL;
	$scope.beginningL;
	$scope.endL;

	$scope.dtOptionsL = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElementsL, $scope.beginningL, $scope.endL);
	});

	$scope.dtColumnDefsL = getDTOptions.notSortableAll(7);

	$scope.searchR = {};
	$scope.totalElementsR;
	$scope.beginningR;
	$scope.endR;

	$scope.dtOptionsR = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElementsR, $scope.beginningR, $scope.endR);
	});

	$scope.dtColumnDefsR = getDTOptions.notSortableAll(6);

	$scope.searchW = {};
	$scope.totalElementsW;
	$scope.beginningW;
	$scope.endW;

	$scope.dtOptionsW = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElementsW, $scope.beginningW, $scope.endW);
	});

	$scope.dtColumnDefsW = getDTOptions.notSortableAll(6);

	//-------------------------------------------------------------------------------------------------------------------------------------

	loans.$promise.then(function(data) {
		setPagedLoans(data);
	});

	restores.$promise.then(function(data) {
		setPagedRestores(data);
	});

	welcomeKits.$promise.then(function(data) {
		setPagedWelcomeKits(data);
	});

	shippingCatalog.$promise.then(function(data) {
		$scope.shippingCatalog = data;
	});

	provinces.$promise.then(function(data) {
		$scope.provinces = data;
	});

	$scope.findLoans = function() {
		rest("loan/findLoans").post($scope.searchL, function(data) {
			setPagedLoans(data);
		});
	}

	$scope.findWelcomeKits = function() {
		rest("welcomeKit/findWelcomeKits").post($scope.searchW, function(data) {
			setPagedWelcomeKits(data);
		});
	}

	$scope.clearLoans = function() {
		$scope.searchL = {};
		$scope.findLoans();
	}

	$scope.clearWelcomeKits = function() {
		$scope.searchW = {};
		$scope.findWelcomeKits();
	}

	$scope.LoansPageChanged = function() {
		$scope.searchL.page = $scope.currentPageL - 1;
		$scope.findLoans();
	}

	$scope.welcomeKitsPageChanged = function() {
		$scope.searchW.page = $scope.currentPageW - 1;
		$scope.findWelcomeKits();
	}

	//-------------------------------------------------------------------------------------------------------------------------------------

	$scope.viewLoan = function(loan) {
		$uibModal.open({
			size: 'md',
			backdrop: true,
			templateUrl: 'js/modules/logistic/view/loanModal.html',
			controller: 'LoanModalCtrl',
			resolve: {
				loadPlugin: function($ocLazyLoad) {
					return $ocLazyLoad.load([{
						name: 'Logistic',
						files: ['js/modules/logistic/controller/loanModalCtrl.js']
					}]);
				},

				loan: function(rest) {
					return rest("loan/findOne/:id").get({id: loan.id}, function(data) {
						return data;
					});
				},

				restore: function() {
					return null;
				},

				kit: function() {
					return null;
				}
			}
		});
	}

	$scope.setLoanTracking = function(loan) {
		$uibModal.open(getTrackingModal(loan)).result.then(function(data) {
			let index = $scope.loans.indexOf(loan);
			$scope.loans[index] = data;
		});
	}

	$scope.getReturnDate = function(loan) {
		let date = new Date(loan.deliveryDate);
		date.setDate(date.getDate() + (7*loan.weeks));

		return date;
	}

	$scope.viewRestore = function(restore) {
		$uibModal.open({
			size: 'md',
			backdrop: true,
			templateUrl: 'js/modules/logistic/view/restoreModal.html',
			controller: 'RestoreModalCtrl',
			resolve: {
				loadPlugin: function($ocLazyLoad) {
					return $ocLazyLoad.load([{
						name: 'Logistic',
						files: ['js/modules/logistic/controller/restoreModalCtrl.js']
					}]);
				},

				loan: function() {
					return null;
				},

				restore: function(rest) {
					return rest("restore/findOne/:id").get({id: restore.id}, function(data) {
						return data;
					});
				},

				kit: function() {
					return null;
				}
			}
		});
	}

	$scope.setRestoreTracking = function(restore) {
		$uibModal.open(getTrackingModal(null, restore)).result.then(function(data) {
			let index = $scope.restores.indexOf(restore);
			$scope.restores[index] = data;
		});
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

				loan: function() {
					return null;
				},

				restore: function() {
					return null;
				},

				kit: function(rest) {
					return rest("welcomeKit/findOne/:id").get({id: kit.id}, function(data) {
						return data;
					});
				}
			}
		});
	}

	$scope.setWelcomeKitTracking = function(kit) {
		$uibModal.open(getTrackingModal(null, null, kit)).result.then(function(data) {
			let index = $scope.welcomeKits.indexOf(kit);
			$scope.welcomeKits[index] = data;
		});
	}

	//-------------------------------------------------------------------------------------------------------------------------------------

	function setPagedLoans(data) {
		$scope.loans = data.content;
		$scope.totalElementsL = data.totalElements;
		$scope.beginningL = (Const.tableSize * data.number) + 1;
		$scope.endL = $scope.beginningL + data.numberOfElements - 1;
		$scope.totalPagesL = data.totalPages;
	}

	function setPagedRestores(data) {
		$scope.restores = data.content;
		$scope.totalElementsR = data.totalElements;
		$scope.beginningR = (Const.tableSize * data.number) + 1;
		$scope.endR = $scope.beginningR + data.numberOfElements - 1;
		$scope.totalPagesR = data.totalPages;
	}

	function setPagedWelcomeKits(data) {
		$scope.welcomeKits = data.content;
		$scope.totalElementsW = data.totalElements;
		$scope.beginningW = (Const.tableSize * data.number) + 1;
		$scope.endW = $scope.beginningW + data.numberOfElements - 1;
		$scope.totalPagesW = data.totalPages;
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