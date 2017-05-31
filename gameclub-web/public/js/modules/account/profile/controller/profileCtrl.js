angular.module('Profile').controller('ProfileCtrl', function($scope, $rootScope, provinces, $state, getImageBase64, sweet, rest, getIndexOfArray, notif, $location, ciValidation) {
    $scope.file = {};
    let tempUserInfo;

    provinces.$promise.then(function(data) {
        $scope.provinces = data;

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
    });

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

    $scope.changeAvatar = function() {
        setTimeout(function() {
            angular.element("#avatar_input").trigger('click');
        }, 0);
    }

    $scope.provinceRemoved = function() {
        $rootScope.currentUser.location = null;
    }

    $scope.save = function() {
        let isValid = true;
        if($rootScope.currentUser.document == null || $rootScope.currentUser.document == '') {
            isValid = true;
        } else if($rootScope.currentUser.document != null && $rootScope.currentUser.document.length != 10) {
            isValid = false;
            notif.danger('Tu número de cédula debe tener 10 dígitos');
        } else if(!ciValidation($rootScope.currentUser.document)) {
            isValid = false;
            notif.danger('Ingresa un número de cédula válido');
        }
        if($rootScope.currentUser.province != null && $rootScope.currentUser.location == null) {
            isValid = false;
            notif.danger('Completa tu ciudad');
        }
        if(isValid) {
            sweet.save(function() {
                rest("publicUser/save").post($rootScope.currentUser, function(data) {
                    $rootScope.currentUser = data;
                    sweet.success();
                    sweet.close();
                }, function(error) {
                    sweet.close();
                });
            });
        }
    }

    $scope.resendMailVerification = function() {
        sweet.default("Se reenviará el correo con el link de verificación a tu correo", function() {
            let baseUrl = $location.$$absUrl.substring(0, $location.$$absUrl.indexOf("#!") + 2);

            rest("publicUser/resendVerification").post(baseUrl, function() {
                notif.success("El correo se reenvió con éxito");
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
    }

    $scope.verifyFacebook = function() {
        FB.login(function(response) {
            if (response.authResponse != null) {
                FB.api('/me', {fields: 'name, email'}, function(me) {
                    if (me.email == null) {
                        FB.logout(function(logoutResponse) {
                            notif.warning("El correo electrónico es necesario para verificar tu cuenta. Por favor permite el acceso a tu correo cuando inicies sesión con Facebook");
                        }, response.authResponse.accessToken);
                    } else {
                        $rootScope.currentUser.facebookToken = response.authResponse.accessToken;
                        $rootScope.currentUser.facebookName = me.name;

                        let formData = {
                            user: $rootScope.currentUser
                        };

                        rest("publicUser/save").multipart(formData, function(data) {
                            $rootScope.currentUser = data;
                            notif.success("Cuenta verificada con Facebook");
                        }, function(error) {
                            notif.danger("No se pudo actualizar el token de Facebook. Por favor vuelva a intentarlo");
                        });
                    }
                });
            }
        }, {
            scope: 'public_profile,email',
            auth_type: 'rerequest'
        });
    }

    $scope.getInfoPercentage = function() {
        let percent = 0;
        let factor = 100 / 7;
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
            if ($rootScope.currentUser.billingAddress != null) {
                percent += factor;
            }
            if ($rootScope.currentUser.contactPhone != null) {
                percent += factor;
            }
        }
        percent = Math.round(percent);
        return percent;
    }

    $scope.getIdentityPercentage = function() {
        let percent = 0;
        if ($rootScope.currentUser != null) {
            percent += $rootScope.currentUser.token == null ? 100 : 0;
            percent = Math.round(percent);
        }
        return percent;
    }

    $scope.getFullPercentaje = function() {
        let percent = 0;
        if ($rootScope.currentUser != null) {
            percent += $scope.getInfoPercentage();
            percent += $scope.getIdentityPercentage();
            percent += $rootScope.currentUser.numberOfGames > 0 ? 100 : 0;
            percent = percent / 3.0;
            percent = Math.round(percent);
        }
        return percent;
    }

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

});
