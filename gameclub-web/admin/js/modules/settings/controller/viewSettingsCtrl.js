// angular.module("Settings").controller('ViewSettingsCtrl', function($scope, settingsList, getDTOptions, $state, friendlyUrl, sweet, rest){
angular.module("Settings").controller('ViewSettingsCtrl', function($scope, settingsList, getDTOptions, Const, rest, sweet) {	
	
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	settingsList.$promise.then(function(data){
		$scope.settingsList = data;
	});

	$scope.save = function(data) {
		console.log("Entra");
		console.log("Entra");
		sweet.default("Se actualizará los datos ingresado", function() {
			rest("settings/save").post(	$scope.settingsList, function() {
				sweet.success();
				sweet.close();
			}, function(error) {
				sweet.error(error.data ? error.data.message : error);
			});
		});
	}
	
});