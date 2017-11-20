angular.module("Profile").controller('ChooseAvatarCtrl', function($scope, $rootScope, $uibModalInstance, sweet, rest, avatars, getIndexOfArray) {
    avatars.$promise.then(function(data) {
        $scope.avatars = data;

        if ($rootScope.currentUser.avatar != null) {
            $scope.indexTemp = getIndexOfArray(avatars, 'id', $rootScope.currentUser.avatar.id);
        }
    });

    $scope.cancel = function() {
        $uibModalInstance.close();
    };

    $scope.save = function() {
        $rootScope.currentUser.avatar = $scope.avatars[$scope.indexTemp];
        
        sweet.save(function() {
            rest("publicUser/save").post($rootScope.currentUser, function(data) {
                sweet.success();
                sweet.close();
                $rootScope.currentUser = data;
                $uibModalInstance.close(data);
            }, function(error) {
                sweet.error(error.data != null ? error.data.message : error);
            });
        });
    };

    $scope.chooseThis = function(index) {
        $scope.indexTemp = index;
    };
});