angular.module('Profile').controller('ProfileCtrl', function($scope, $rootScope, provinces, $state, getImageBase64, sweet, rest, getIndexOfArray, notif, $location, ciValidation, $location, $uibModal) {
    $scope.file = {};
    let tempUserInfo;

    $rootScope.$watch("currentUser", function(newValue, oldValue) {
        if (newValue != null) {
            $scope.currentUserTemp = angular.copy($rootScope.currentUser);
            $scope.newUsername = angular.copy($rootScope.currentUser.username);

            if($scope.currentUserTemp.location != null) {
                $scope.currentUserTemp.province = $scope.currentUserTemp.location.parent;
                $scope.currentUserTemp.location = $scope.currentUserTemp.location;
            }
        }
    });

    provinces.$promise.then(function(data) {
        $scope.provinces = data;
    });

    $scope.findCities = function() {
		$scope.currentUserTemp.location = null;
		if($scope.currentUserTemp.province != null) {
            rest("location/findChildrenOf/:code", true).get({code: $scope.currentUserTemp.province.code}, function(data) {
				$scope.locationCities = data;
			});
		}
	};

    $scope.provinceRemoved = function() {
        $scope.currentUserTemp.location = null;
    }

    $scope.save = function() {
        let isValid = true;
        if($scope.currentUserTemp.document == null || $scope.currentUserTemp.document == '') {
            isValid = true;
        } else if($scope.currentUserTemp.document != null && $scope.currentUserTemp.document.length != 10) {
            isValid = false;
            notif.danger('Tu número de cédula debe tener 10 dígitos');
        } else if(!ciValidation($scope.currentUserTemp.document)) {
            isValid = false;
            notif.danger('Ingresa un número de cédula válido');
        }
        if($scope.currentUserTemp.province != null && $scope.currentUserTemp.location == null) {
            isValid = false;
            notif.danger('Completa tu ciudad');
        }
        if(isValid) {
            sweet.save(function() {
                rest('publicUser/save').post($scope.currentUserTemp, function(data) {
                    $rootScope.currentUser = data;
                    $scope.currentUserTemp = data;
                    if($scope.currentUserTemp.location != null) {
                        $scope.currentUserTemp.province = $scope.currentUserTemp.location.parent;
                        $scope.currentUserTemp.location = $scope.currentUserTemp.location;
                    }
                    sweet.success();
                    sweet.close();
                }, function(error) {
                    sweet.close();
                });
            });
        }
    };

    $scope.changeMail = function() {
        let isValid = true;
        let emailValidation = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if($scope.newUsername == null || $scope.newUsername == '') {
            isValid = false;
            notif.danger('Completa tu correo electrónico')
        } else if(!emailValidation.test($scope.newUsername)) {
            isValid = false;
            notif.danger('Ingresa un correo electrónico válido')
        }
        if(isValid) {
            sweet.save(function() {
                rest('publicUser/changeMail').post({oldUsername: $rootScope.currentUser.username, newUsername: $scope.newUsername, baseUrl: $location.$$absUrl.substring(0, $location.$$absUrl.indexOf("#!") + 2)}, function(data) {
                    $rootScope.currentUser = data;
                    $scope.currentUserTemp = data;
                    sweet.success();
                    sweet.close();
                }, function(error) {
                    sweet.error(error.data.message);
                });
            });
        }
    };

    $scope.resendMailVerification = function() {
        sweet.default('Se reenviará el correo con el link de verificación a tu correo', function() {
            let baseUrl = $location.$$absUrl.substring(0, $location.$$absUrl.indexOf('#!') + 2);

            rest('publicUser/resendVerification').post(baseUrl, function() {
                notif.success('El correo se reenvió con éxito');
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
                            notif.warning('El correo electrónico es necesario para verificar tu cuenta. Por favor permite el acceso a tu correo cuando inicies sesión con Facebook');
                        }, response.authResponse.accessToken);
                    } else {
                        $rootScope.currentUser.facebookToken = response.authResponse.accessToken;
                        $rootScope.currentUser.facebookName = me.name;

                        let formData = {
                            user: $rootScope.currentUser
                        };

                        rest('publicUser/save').multipart(formData, function(data) {
                            $rootScope.currentUser = data;
                            notif.success('Cuenta verificada con Facebook');
                        }, function(error) {
                            notif.danger('No se pudo actualizar el token de Facebook. Por favor vuelva a intentarlo');
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
            if ($scope.currentUserTemp.province != null) {
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
        if(!$scope.isEditableMean) {
            $scope.newUsername = angular.copy($rootScope.currentUser.username);
        }
    };

    $scope.chooseAvatar = function() {
		let modal = $uibModal.open({
			size: 'lg',
			backdrop: 'static',
			templateUrl: 'js/modules/account/profile/view/chooseAvatar.html',
			controller: chooseAvatarCtrl,
            resolve: {
                avatars: function(openRest) {
                    return openRest('avatar/findAll', true).get(null, function(data) {
                        return data;
                    });
                }
            }
		});
	};

    let chooseAvatarCtrl = function($scope, $uibModalInstance, sweet, rest, avatars, $rootScope, getIndexOfArray) {
        $scope.isSaving = false;
        avatars.$promise.then(function(data) {
            $scope.avatars = data;
            if($rootScope.currentUser.avatar != null) {
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
	}

});
