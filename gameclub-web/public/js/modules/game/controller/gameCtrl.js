angular.module('Game').controller('GameCtrl', function($scope, $rootScope, game, consoleId, availableGames, mostPlayed, $state, Const, openRest, getImageBase64, $location, forEach, getImageBase64, notif, $uibModal, sweet, rest, notif, SweetAlert, geolocation, friendlyUrl) {
    let currentPage = 0;

    $scope.validCards = [];

    $scope.priceChartingGM = parseFloat($rootScope.settings['STPCHG'].value);
    priceChartingGMLoan = 0.0;
    $scope.gameLoanPCH = parseFloat($rootScope.settings['STGRCO'].value);

    let priceChartingGM = parseFloat($rootScope.settings[Const.settings.priceChartingGames].value);
    let priceChartingGMLoan = 0.0;
    let gameLoanPCH = parseFloat($rootScope.settings[Const.settings.weekShippingCost].value);

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

        mostPlayed.$promise.then(function(data) {
            $scope.mostPlayed = data;
        });
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
        let filter = {
            gameId: $scope.game.id,
            consoleId: $scope.console.selected.console.id
        };

        openRest("game/getAvailableGames").post(filter, function(data) {
            $scope.availableGames = [];
            setPagedAvailableGames(data);
        });
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
        if ($rootScope.currentUser == null) {
            $state.go("^.login", {redirect: $location.$$absUrl});
        } else {
            if (getInfoPercentage() >= 100 && getIdentityPercentage() >= 100 && $rootScope.currentUser.isReady) {
                $scope.loanGame = cross;
                $scope.loanViewOpen = true;

                $scope.loan = {
                    publicUserGame: cross,
                    weeks: 1,
                    gamerAddress: $rootScope.currentUser.billingAddress,
                    gamerReceiver: ($rootScope.currentUser.name + ' ' + $rootScope.currentUser.lastName)
                };
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

                if (!$rootScope.currentUser.isReady) {
                    notif.danger("Para poder solicitar un juego primero debes haber recibido tu Welcome Kit. Sube tu primer juego y te enviaremos uno gratis");
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
        currentPage = 0;
        $scope.availableGames = [];

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
        geolocation().result.then(function(pos) {
            $scope.loan.gamerGeolocation = pos;
        });
    }

    $scope.continueRequest = function() {
        let failedValidation = false;

        if ($scope.loan.weeks == null) {
            notif.danger("Debe seleccionar el número de semanas");
            failedValidation = true;
        }

        if ($scope.loan.gamerAddress == null || $scope.loan.gamerAddress == '') {
            notif.danger("El campo 'Dirección' es obligatorio");
            failedValidation = true;
        }

        if ($scope.loan.gamerGeolocation == null) {
            notif.danger("Debe proporcionar su geolocalización");
            failedValidation = true;
        }

        if ($scope.loan.gamerReceiver == null || $scope.loan.gamerReceiver == '') {
            notif.danger("El campo 'Persona de entrega' es obligatorio");
            failedValidation = true;
        }

        if (!failedValidation) {
            $scope.paymentViewOpen = true;
            angular.element(".payment-view").toggle(250);
        }
    }

    $scope.requestLoan = function() {
        let failedValidation = false;

        if ($scope.loan.weeks == null) {
            notif.danger("Debe seleccionar el número de semanas");
            failedValidation = true;
        }

        if ($scope.loan.gamerAddress == null || $scope.loan.gamerAddress == '') {
            notif.danger("El campo 'Dirección' es obligatorio");
            failedValidation = true;
        }

        if ($scope.loan.gamerGeolocation == null) {
            notif.danger("Debe proporcionar su geolocalización");
            failedValidation = true;
        }

        if ($scope.loan.gamerReceiver == null || $scope.loan.gamerReceiver == '') {
            notif.danger("El campo 'Persona de entrega' es obligatorio");
            failedValidation = true;
        }

        if ($scope.loan.payment == null) {
            notif.danger("Por favor seleccione una forma de pago");
            failedValidation = true;
        }

        /**** Revisar con bugs ******/
        if (!failedValidation) {
            console.log("Valida");
            if($rootScope.currentUser.paymentMethods.length = 1){
                console.log("Hay una tarjetA");
                console.log($rootScope.currentUser.paymentMethods[0].status);
            }
            
            sweet.default("Se enviará una solicitud de prestamo al propietario del juego", function() {
                console.log("loan: ", $scope.loan);
                console.log($rootScope.currentUser.paymentMethods[0].status);
                rest("loan/requestGame").post($scope.loan, function(data) {
                    notif.success("Solicitud enviada con éxito");
                    $rootScope.currentUser = data;
                    sweet.close();
                    $state.go("gameclub.account.messages");
                }, function(error) {
                    sweet.close();
                });
            });
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

    $scope.loadMoreGames = function() {
        currentPage++;
        filterAvailibleGames();
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

    $scope.viewOwnerRating = function(owner) {
        $state.go("^.publicProfile", {id: owner.id, alias: friendlyUrl(owner.name + ' ' + owner.lastName.substring(0, 1))});
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
        if ($scope.availableGames == null) {
            $scope.availableGames = [];
        }

        Array.prototype.push.apply($scope.availableGames, data.content);
        $scope.lastPage = data.last;
        currentPage = data.number;
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
            page: currentPage
        };

        openRest("game/getAvailableGames").post(filter, function(data) {
            setPagedAvailableGames(data);
        });
    }
});
