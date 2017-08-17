angular.module("WelcomeKit").controller('DetailsWelcomeKitCtrl', function($scope, $uibModalInstance, kit) {
	kit.$promise.then(function(data) {
		$scope.kit = data;
	});

	$scope.ok = function() {
		$uibModalInstance.close();
	}
});