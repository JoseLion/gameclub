angular.module("Refund").controller('ViewRefundCtrl',function($scope, $rootScope, transactions, getDTOptions, Const, sweet, rest){

	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	$scope.dtColumnDefs = getDTOptions.notSortableAll(7);

	transactions.$promise.then(function(data){
		setPagedData(data);
	});

	$scope.executeRefund = function(data){
		sweet.default("Se realizarà el reembolso a tu tarjeta", function() {
			rest("refund/applyRefund").post(data, function(response) {
				let index = $scope.transactions.indexOf(data);
				$scope.transactions[index] = response;
				sweet.success();
				sweet.close();
			}, function(error) {
				sweet.error(error.data ? error.data.message : error);
			});
		});
	}

	$scope.find = function() {
		if ($scope.search.catalog != null) {
			$scope.search.catalogId = $scope.search.catalog.id;
		}
		console.log($scope.search);
		if($scope.search.statusRefund){
			$scope.search.statusRefund = 'ACREDITADO';
		} else {
			$scope.search.statusRefund = 'DEBITADO';
		}
		console.log($scope.search);
		rest("refund/findRefound").post($scope.search, function(data) {
			setPagedData(data);
		});
	}

	$scope.clean = function() {
		$scope.search = {};
		rest("refund/findRefunds", true).post(function(data) {
			$scope.transactions = data;
		});
	}

	function setPagedData(data) {
		$scope.transactions = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});