angular.module("Console").controller('ViewConsolesCtrl', function($scope, consoles, getDTOptions, $state, friendlyUrl, sweet, rest) {
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	consoles.$promise.then(function(data) {
		$scope.consoles = data;
	});

	$scope.addConsole = function() {
		$state.go("^.addConsole");
	}

	$scope.editConsole = function(consl) {
		$state.go("^.editConsole", {id: consl.id, name: friendlyUrl(consl.name)});
	}

	$scope.changeStatus = function(consl) {
		sweet.changeStatus(function() {
			rest("console/changeStatus/:id").get({id: consl.id}, function(data) {
				let index = $scope.consoles.indexOf(consl);
				$scope.consoles[index].status = data;

				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}
});