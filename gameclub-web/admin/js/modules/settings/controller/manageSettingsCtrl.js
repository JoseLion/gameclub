angular.module("Settings").controller('ManageSettingCtrl',function($scope, settingObj, sweet, rest, $state, setCat){
	$scope.settingObj = {};
	$scope.cate = {};
	// console.log("Categorias " + setCat);
	if (settingObj != null) {
		settingObj.$promise.then(function(data){
			$scope.settingObj = data;
		});
	}

	setCat.$promise.then(function(data){
		// console.log("Categorias " + data);
		$scope.cate = data;
	});
	// angular.forEach(setCat, function(data){
	// 	console.log(setCatt);
        // $scope.options.push(setCat);
    // });

	console.log("Categorias " + $scope.categories);

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
		// $scope.data = data.toUpperCase();

	}

	$scope.upper2 = function(e) {
		console.log("Entra  " + e);
	     e.value = e.value.toUpperCase();
	}

});