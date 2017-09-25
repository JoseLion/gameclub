angular.module("ShippingPrice").controller('ShippingPriceCtrl', function($scope, shipping, getDTOptions, Const, rest, sweet) {
	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	shipping.$promise.then(function(data) {
		setPagedData(data);
	});

	$scope.save = function(data) {
		sweet.default("Se actualizará los datos con el archivo ingresado", function(data) {
			rest("shippingPrice/updatePrices").multipart({file: $scope.file}, function() {
				sweet.success();
				setPagedData(data);
				sweet.close();
			}, function(error) {
				sweet.error(error.data ? error.data.message : error);
			});
		});
	}

	$scope.find = function() {
		rest("shippingPrice/findShippingPrices").post($scope.search, function(data) {
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

	function setPagedData(data) {
		$scope.shippingPrices = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});