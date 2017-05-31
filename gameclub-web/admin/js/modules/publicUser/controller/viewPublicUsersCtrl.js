angular.module("PublicUser").controller('ViewPublicUsersCtrl', function($scope, publicUsers, getDTOptions, $state, friendlyUrl, sweet, rest, Const) {

	$scope.search = {};
	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});
	$scope.dtColumnDefs = getDTOptions.notSortableAll(4);

	publicUsers.$promise.then(function(data) {
		setPagedData(data);
	});

	$scope.find = function() {
		if($scope.search.startDate == null) {
			delete $scope.search.startDate;
		}
		if($scope.search.endDate == null) {
			delete $scope.search.endDate;
		}
		rest("publicUser/findPublicUsers").post($scope.search, function(data) {
			setPagedData(data);
		});
	};

	$scope.clear = function() {
		$scope.search = {};
		$scope.find();
	};

	$scope.pageChanged = function() {
		$scope.search.page = $scope.currentPage - 1;
		$scope.find();
	};

	$scope.editUser = function(user) {
		$state.go("^.editPublicUser", {id: user.id, fullName: friendlyUrl(user.name + " " + user.lastName)});
	};

	$scope.changeStatus = function(user) {
		sweet.changeStatus(function() {
			rest("publicUser/changeStatus/:id").get({id: user.id}, function(data) {
				let index = $scope.publicUsers.indexOf(user);
				$scope.publicUsers[index].status = data;
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	};

	function setPagedData(data) {
		$scope.publicUsers = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}

});
