angular.module("Game").controller('ViewGamesCtrl', function($scope, games, consoles, categories, getDTOptions, $state, sweet, rest, Const, friendlyUrl) {
	$scope.search = {};
	$scope.totalElements;
	$scope.beginning;
	$scope.end;

	$scope.dtOptions = getDTOptions.paged().withOption('infoCallback', function(settings, start, end, max, total, pre) {
		return getDTOptions.infoCallback($scope.totalElements, $scope.beginning, $scope.end);
	});

	$scope.dtColumnDefs = getDTOptions.notSortableAll(6);

	games.$promise.then(function(data) {
		setPagedData(data);
	});

	consoles.$promise.then(function(data) {
		$scope.consoles = data;
	});

	categories.$promise.then(function(data) {
		$scope.categories = data;
	});

	$scope.find = function() {
		rest("game/findGames").post($scope.search, function(data) {
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

	$scope.addGame = function() {
		$state.go("^.addGame");
	}

	$scope.editGame = function(game) {
		$state.go("^.editGame", {id: game.id, name: friendlyUrl(game.name)});
	}
 
	$scope.changeStatus = function(game) {
		sweet.changeStatus(function() {
			rest("game/changeStatus/:id").get({id: game.id}, function(data) {
				let index = $scope.games.indexOf(game);
				$scope.games[index].status = data;
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	function setPagedData(data) {
		console.log("Data:", data);
		$scope.games = data.content;
		$scope.totalElements = data.totalElements;
		$scope.beginning = (Const.tableSize * data.number) + 1;
		$scope.end = $scope.beginning + data.numberOfElements - 1;
		$scope.totalPages = data.totalPages;
	}
});