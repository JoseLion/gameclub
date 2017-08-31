angular.module('PublicProfile').controller('PublicProfileCtrl', function($scope, user) {
	user.$promise.then(function(data) {
		$scope.user = data;
	});
});	