angular.module("Settings").controller('ManageSettingCtrl',function($scope, settingObj, sweet, rest, $state, setCat){
	$scope.settingObj = {};
	$scope.cate = {};
	if (settingObj != null) {
		settingObj.$promise.then(function(data){
			$scope.settingObj = data;
		});
	}

	setCat.$promise.then(function(data){
		$scope.cate = data;
	});

	$scope.save = function(){
		sweet.save(function(){
			rest("settings/save").post($scope.settingObj, function(){
				sweet.success();
				sweet.close();
				$scope.cancel();
			}, function(error){
				sweet.error(error.data.message);
			});
		});
	}

	$scope.cancel = function(){
		$state.go("^.viewSettings");
	}

	$scope.upper = function(test){
		console.log("Entra cambio: " + text);
	}

	$scope.upper2 = function(e) {
	     e.value = e.value.toUpperCase();
	}

});