angular.module('Game').controller('GameCtrl', function($scope, $rootScope, game, consoleId, availableGames, $state, Const, openRest, getImageBase64, $location, forEach, getImageBase64, notif, $uibModal, sweet, rest, notif, SweetAlert) {
    if (game != null) {
        game.$promise.then(function(data) {
            $scope.game = data;
            openRest("archive/downloadFile").download({name: $scope.game.banner.name, module: $scope.game.banner.module}, function(data) {
                $scope.background = {
                    background: "url('" + getImageBase64(data, $scope.game.banner.type) + "') center bottom / 100% no-repeat"
                };
            });

            forEach($scope.game.consoles, function(gameConsole) {
                openRest("archive/downloadFile").download({name: gameConsole.console.blackLogo.name, module: gameConsole.console.blackLogo.module}, function(data) {
    				gameConsole.console.blackLogoBase64 = getImageBase64(data, gameConsole.console.blackLogo.type);
    			});
            });

            $scope.console = {};
            if (consoleId != null) {
                forEach($scope.game.consoles, function(cross) {
                    if (cross.console.id == consoleId) {
                        $scope.console.selected = cross;
                        return 'break';
                    }
                });
            } else {
                $scope.console.selected = $scope.game.consoles[0];
            }
        });

        availableGames.$promise.then(function(data) {
            setPagedAvailableGames(data);
        });

        $scope.filters = [
            {icon: 'gc-send', sort: 'status', desc: true, active: false},
            {icon: 'gc-address', sort: 'publicUser.location.name', desc: true, active: false},
            {icon: 'gc-usd', sort: 'cost', desc: true, active: false},
            {icon: 'gc-rating', sort: 'publicUser.rating', desc: true, active: false},
            {icon: 'gc-cd', sort: 'integrity', desc: true, active: false},
            {icon: 'gc-filter', sort: ''}
        ];
    } else {
        $state.go(Const.mainState);
    }

    $scope.addToLibrary = function() {
        if (getInfoPercentage() >= 100 && getIdentityPercentage() >= 100) {
            if ($rootScope.currentUser.numberOfGames < $rootScope.currentUser.gamesLimit) {
                $state.go("^.account.myGames", {game: $scope.game, consoleSelected: $scope.console.selected});
            } else {
                SweetAlert.swal("Lo sentimos...", "Solamente puedes subir " + $rootScope.currentUser.gamesLimit + " juegos, si deseas subir más, contactanos", "warning");
            }
        } else {
            if (getInfoPercentage() < 100 && getIdentityPercentage() < 100) {
                notif.danger("Primero debes completar tu información de contacto y verificar tu identidad para cargar juegos a tu perfil");
            } else  {
                if (getInfoPercentage() < 100 && getIdentityPercentage() >= 100) {
                    notif.danger("Primero debes completar tu información de contacto para cargar juegos a tu perfil");
                }

                if (getInfoPercentage() >= 100 && getIdentityPercentage() < 100) {
                    notif.danger("Primero debes verificar tu identidad para cargar juegos a tu perfil");
                }
            }
        }
    }

    $scope.login = function() {
        $state.go("^.login", {redirect: $location.$$absUrl});
    }

    $scope.consoleSelected = function() {
        console.log('FIND AVAILABLES BY CONSOLE: ', $scope.console.selected);
    }

    $scope.getPreviousGame = function() {
        let temp = $scope.mostPlayed.splice(0, 1);
        $scope.mostPlayed[3] = temp[0];
    }

    $scope.getNextGame = function() {
        let temp = $scope.mostPlayed.splice(-1, 1);
        $scope.mostPlayed.unshift(temp[0]);
    }

    $scope.openLoanView = function(cross) {
        if (getInfoPercentage() >= 100 && getIdentityPercentage() >= 100) {
            if ($rootScope.currentUser != null) {
                $scope.loanGame = cross;
                $scope.loanViewOpen = true;

                $scope.loan = {
                    weeks: 1,
                    address: $rootScope.currentUser.billingAddress,
                    rceiver: $rootScope.currentUser.rceiver
                };
            } else {
                $state.go("^.login", {redirect: $location.$$absUrl});
            }
        } else {
            if (getInfoPercentage() < 100 && getIdentityPercentage() < 100) {
                notif.danger("Primero debes completar tu información de contacto y verificar tu identidad para solicitar juegos");
            } else  {
                if (getInfoPercentage() < 100 && getIdentityPercentage() >= 100) {
                    notif.danger("Primero debes completar tu información de contacto para solicitar juegos");
                }

                if (getInfoPercentage() >= 100 && getIdentityPercentage() < 100) {
                    notif.danger("Primero debes verificar tu identidad para solicitar juegos");
                }
            }
        }
    }

    $scope.closeLoanView = function() {
        $scope.loanGame = null;
        $scope.loanViewOpen = false;

        $scope.loan = {};
    }

    $scope.doFilter = function(filter) {
        forEach($scope.filters, function(fltr) {
            if (fltr.icon == filter.icon && fltr.active == true) {
                filter.desc = !filter.desc;
            }

            fltr.active = false;
        });

        if (filter.sort != '') {
            filter.active = true;
        }
        
        filterAvailibleGames();
    }

    $scope.openMapsModal = function() {
        let modal = $uibModal.open({
            size: 'md',
            backdrop: 'static',
            templateUrl: 'mapsModal.html',
            controller: function($scope, $uibModalInstance, NgMap, $http, Const) {
                $scope.currentPos = {lat: 0, lng: 0};

                NgMap.getMap().then(function(map) {
                    google.maps.event.trigger(map, "resize");
                    $scope.mapLoaded = true;

                    let marker = new google.maps.Marker({
                        position: $scope.currentPos,
                        map: map,
                        title: 'Drag me',
                        draggable: true
                    });

                    marker.addListener('dragend', function(event) {
                        $scope.currentPos = {
                            lat: event.latLng.lat(),
                            lng: event.latLng.lng()
                        };
                    });

                    $scope.$watch("currentPos", function(newValue, oldValue) {
                        if (newValue != null) {
                            marker.setPosition(newValue);
                            map.setCenter(newValue);
                            map.setZoom(14);
                        }
                    });

                    $scope.centerInMarker = function() {
                        map.panTo(marker.getPosition());
                        marker.setMap(null);
                    }
                });

                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(function(pos) {
                        $scope.currentPos = {
                            lat: pos.coords.latitude,
                            lng: pos.coords.longitude
                        };
                    }, function(error) {
                        handleGeolocationError();
                    });
                } else {
                    handleGeolocationError();
                }

                function handleGeolocationError() {
                    $http.post("https://www.googleapis.com/geolocation/v1/geolocate?key=" + Const.google.geolocation).then(function(response) {
                        $scope.currentPos = response.data.location;
                    });
                }

                $scope.ok = function() {
                    $scope.centerInMarker();
                    $uibModalInstance.close($scope.currentPos);
                }

                $scope.cancel = function() {
                    $uibModalInstance.dismiss();
                }
            },
            resolve: {
                loadPlugin: function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'ngMap',
                        files: ['js/core/plugins/ng-map/ng-map.min.js']
                    }]);
                }
            }
        });

        modal.result.then(function(pos) {
            $scope.loan.geoLocation = pos;
        });
    }

    $scope.continueRequest = function() {
        let faildValidation = false;

        if ($scope.loan.weeks == null) {
            notif.danger("Debe seleccionar el número de semanas");
            faildValidation = true;
        }

        if ($scope.loan.address == null || $scope.loan.address == '') {
            notif.danger("El campo 'Dirección' es obligatorio");
            faildValidation = true;
        }

        if ($scope.loan.geoLocation == null) {
            notif.danger("Debe proporcionar su geolocalización");
            faildValidation = true;
        }

        if ($scope.loan.receiver == null || $scope.loan.receiver == '') {
            notif.danger("El campo 'Persona de entrega' es obligatorio");
            faildValidation = true;
        }

        if (!faildValidation) {
            $scope.paymentViewOpen = true;
            angular.element(".payment-view").toggle(250);
        }
    }

    $scope.requestLoan = function() {
        let faildValidation = false;

        if ($scope.loan.weeks == null) {
            notif.danger("Debe seleccionar el número de semanas");
            faildValidation = true;
        }

        if ($scope.loan.address == null || $scope.loan.address == '') {
            notif.danger("El campo 'Dirección' es obligatorio");
            faildValidation = true;
        }

        if ($scope.loan.geoLocation == null) {
            notif.danger("Debe proporcionar su geolocalización");
            faildValidation = true;
        }

        if ($scope.loan.receiver == null || $scope.loan.receiver == '') {
            notif.danger("El campo 'Persona de entrega' es obligatorio");
            faildValidation = true;
        }

        if ($scope.loan.payment == null) {
            notif.danger("Por favor seleccione una forma de pago");
            faildValidation = true;
        }

        if (!faildValidation) {
            console.log("TO DO: Request loan throgh messaging process");
        }
    }

    $scope.removeCard = function(method) {
        sweet.default("Se eliminará la forma de pago permanentemente", function() {
            rest("publicUser/deletePaymentMethod/:subscriptionId").get({subscriptionId: method.id}, function(data) {
                $rootScope.currentUser = data;
                notif.success("Forma de pago eliminada con éxito");
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
    }

    function getInfoPercentage() {
        let percent = 0;
        let factor = 100 / 6;

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

    function getIdentityPercentage() {
        let percent = 0;
        if ($rootScope.currentUser != null) {
            percent += $rootScope.currentUser.token == null ? 100 : 0;
            percent = Math.round(percent);
        }
        return percent;
    }

    function setPagedAvailableGames(data) {
        $scope.availableGames = data.content;
        $scope.lastPage = data.last;
        $scope.currentPage = data.number;
    }

    function filterAvailibleGames() {
        let activeFilter;
        forEach($scope.filters, function(filter) {
            if (filter.active == true) {
                activeFilter = filter;
                return 'break';
            }
        });

        let filter = {
            gameId: $scope.game.id,
            consoleId: $scope.console.selected.console.id,
            sort: activeFilter != null ? activeFilter.sort : '',
            desc: activeFilter != null ? activeFilter.desc : true,
            page: $scope.currentPage
        };

        openRest("game/getAvailableGames").post(filter, function(data) {
            setPagedAvailableGames(data);
        });
    }
});
