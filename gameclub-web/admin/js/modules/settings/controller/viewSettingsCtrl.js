angular.module("Settings").controller('ViewSettingsCtrl', function($scope, settingsList, getDTOptions, $state, friendlyUrl, sweet, rest){
	
	$scope.dtOptions = getDTOptions.unpaged("fTgitp");
	$scope.dtColumnDefs = getDTOptions.notSortableAll(3);

	settingsList.$promise.then(function(data){
		$scope.settingsList = data;
	});

	/*$scope.find = function() {
		rest("settings/findAll",true).post(function(data){
			$scope.settings = data;
		});
	}

	$scope.typeVal = function(val){
		var $scope.valu = "";
		switch (val.type) {
		    case "TOSNBR": $scope.valu = "$"; break;
		    case "TOSPRC": $scope.valu = "%"; break;
		    default: $scope.valu = "Semanas"; break;
		}
		return $scope.valu;
	}*/

	$scope.addSettings = function() {
		$state.go("^.addSetting");
	}

	$scope.editSetting = function(settingObj) {
		$state.go("^.editSetting",{fullName: friendlyUrl(settingObj.name), id: settingObj.id});
	}
});