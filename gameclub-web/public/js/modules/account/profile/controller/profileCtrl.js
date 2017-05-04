angular.module('Profile').controller('ProfileCtrl', function($scope, $rootScope, provinces, $state, getImageBase64, sweet, rest, getIndexOfArray) {
    $scope.contactInfo = {};
    $scope.contactMean = {fb:'Pablo Ponce'};

    $scope.file = {};
    let tempUserInfo;

    provinces.$promise.then(function(data) {
        $scope.provinces = data;
    });

    $scope.changeAvatar = function() {
        setTimeout(function() {
            angular.element("#avatar_input").trigger('click');
        }, 0);
    }

    $scope.provinceRemoved = function() {
        console.log("REMOVED");
        $rootScope.currentUser.location = null;
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

    $scope.getInfoPercentage = function() {
        let percent = 0;
        let factor = 100 / 13;

        if ($rootScope.currentUser != null) {
            if ($rootScope.currentUser.document != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.birthDate != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.profession != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.province != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.location != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.firstAddress != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.firstReceiver != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.firstMorningStartTime != null && $rootScope.currentUser.firstMorningEndTime != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.firstNoonStartTime != null && $rootScope.currentUser.firstNoonEndTime != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.secondAddress != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.secondReceiver != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.secondMorningStartTime != null && $rootScope.currentUser.secondMorningEndTime != null) {
                percent += factor;
            }

            if ($rootScope.currentUser.secondNoonStartTime != null && $rootScope.currentUser.secondNoonEndTime != null) {
                percent += factor;
            }
        }

        percent = Math.round(percent);
        $scope.InfoPercent = percent;
        return percent;
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

    $rootScope.$watch('currentUser.location', function(newValue, oldValue) {
        if (newValue != null && $rootScope.currentUser.province == null) {
            let index = getIndexOfArray($scope.provinces, 'id', newValue.parent.id);
            $rootScope.currentUser.province = $scope.provinces[index];
        }
    });

    $scope.$watch('currentUser.province', function(newValue, oldValue) {
        if (newValue == null && $rootScope.currentUser != null) {
            $rootScope.currentUser.location = null;
        }
    });

    $scope.editInfo = function() {
        $scope.isEditable = !$scope.isEditable;

        if($scope.isEditable) {
            tempUserInfo = angular.copy($rootScope.currentUser);
        } else {
            $rootScope.currentUser = angular.copy(tempUserInfo);
            delete tempUserInfo;
        }
    }

    $scope.isEditableMean = false;
    $scope.editContactMean = function() {
        $scope.isEditableMean = !$scope.isEditableMean;
        if($scope.isEditableMean) {
            $scope.contactMeanTmp = angular.copy($scope.contactMean);
        } else {
            $scope.contactMean = angular.copy($scope.contactMeanTmp);
        }
    };

    $scope.games = [1];
});
