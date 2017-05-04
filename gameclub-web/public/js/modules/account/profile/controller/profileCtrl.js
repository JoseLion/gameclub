angular.module('Profile').controller('ProfileCtrl', function($scope, $rootScope, provinces, $state, getImageBase64, sweet, rest) {
    $scope.contactInfo = {};
    $scope.contactMean = {fb:'Pablo Ponce'};

    $scope.file = {};

    provinces.$promise.then(function(data) {
        $scope.provinces = data;
    });

    $scope.changeAvatar = function() {
        setTimeout(function() {
            angular.element("#avatar_input").trigger('click');
        }, 0);
    }

    $scope.save = function() {
        sweet.save(function() {
            let formData = {
                user: $rootScope.currentUser,
                avatar: $scope.file.avatar
            };

            rest("publicUser/save").multipart(formData, function(data) {
                $rootScope.currentUser = data;
                sweet.success();
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
    }

    $scope.$watch('file.avatar', function(newValue, oldValue) {
        if (newValue != null) {
            let reader = new FileReader();
            reader.readAsArrayBuffer(newValue);

            reader.onload = function() {
                setTimeout(function() {
                    $scope.$apply(function() {
                        $scope.file.base64 = getImageBase64(reader.result, newValue.type);
                    });
                }, 0);
            }
        } else {
            $scope.file = {};
            let img = angular.element("#avatar_img");
            img.attr('src', img.attr('placeholder'));
        }
    });

    $scope.isEditContact = false;
    $scope.editContact = function() {
        $scope.isEditContact = !$scope.isEditContact;
        if($scope.isEditContact) {
            $scope.contactInfoTmp = angular.copy($scope.contactInfo);
        } else {
            $scope.contactInfo = angular.copy($scope.contactInfoTmp);
        }
    };

    $scope.isEditContactMean = false;
    $scope.editContactMean = function() {
        $scope.isEditContactMean = !$scope.isEditContactMean;
        if($scope.isEditContactMean) {
            $scope.contactMeanTmp = angular.copy($scope.contactMean);
        } else {
            $scope.contactMean = angular.copy($scope.contactMeanTmp);
        }
    };

    $scope.games = [1];
});
