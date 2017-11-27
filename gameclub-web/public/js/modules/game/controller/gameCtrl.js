angular.module('Game').controller('GameCtrl', function($scope, $rootScope, game, consoleId, availableGames, mostPlayed, addCardError, $state, Const, openRest, getImageBase64, $location, forEach, getImageBase64, notif, $uibModal, sweet, rest, notif, SweetAlert, geolocation, friendlyUrl) {
    let currentPage = 0;
    let priceChartingGMLoan = 0.0;
    $scope.validCards = [];
    let taxes = Const.iva;
    let feeLoanGamerPercentage = parseFloat($rootScope.settings[Const.settings.feeLoanGamer].value);

    if (addCardError != null) {
        $scope.addCardError = addCardError;
    }

    $scope.balance = {
        value: 0.0,
        options: {
            hidePointerLabels: true,
            hideLimitLabels: true,
            showSelectionBar: true,
            step: 0.01,
            precision: 0.01,
            floor: 0.0
        }
    };

    if (game != null) {
        game.$promise.then(function(data) {
            $scope.game = data;

            openRest("archive/downloadFile").download({name: $scope.game.banner.name, module: $scope.game.banner.module}, function(dwFile) {
                $scope.background = {
                    background: "url('" + getImageBase64(dwFile, $scope.game.banner.type) + "') center bottom / 100% no-repeat"
                };
            });

            forEach($scope.game.consoles, function(gameConsole) {
                openRest("archive/downloadFile").download({name: gameConsole.console.blackLogo.name, module: gameConsole.console.blackLogo.module}, function(dwFile) {
    				gameConsole.console.blackLogoBase64 = getImageBase64(dwFile, gameConsole.console.blackLogo.type);
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

        mostPlayed.$promise.then(function(mostP) {
            $scope.mostPlayed = mostP;
        });

        if($rootScope.currentUser != null && $rootScope.currentUser.location != null) {
            var code = "";
            code = $rootScope.currentUser.location.parent.code;
            if($rootScope.currentUser.location.parent != null){
                rest("location/findChildrenOf/:code", true).get({code: code}, function(location) {
                    $scope.locationCities = location;
                });
            }
        }
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
            if (getInfoPercentage() >= 100 && getIdentityPercentage() >= 100/* && $rootScope.currentUser.isReady*/) {
                $scope.loanGame = cross;
                $scope.loanViewOpen = true;
                $scope.paymentViewOpen = false;
                $scope.shippingCost = $scope.loanGame.shippingCost * 2;

                $scope.loan = {
                    publicUserGame: cross,
                    weeks: 1,
                    gamerAddress: $rootScope.currentUser.billingAddress,
                    gamerGeolocation: $rootScope.currentUser.geolocation,
                    gamerReceiver: $rootScope.currentUser.receiver != null ? $rootScope.currentUser.receiver : ($rootScope.currentUser.name + ' ' + $rootScope.currentUser.lastName)
                };

                $scope.weekSelected();
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

                /*if (!$rootScope.currentUser.isReady) {
                    notif.danger("Para poder solicitar un juego primero debes haber recibido tu Welcome Kit. Sube tu primer juego y te enviaremos uno gratis");
                }*/
            }
        }
    }

    $scope.closeLoanView = function() {
        $scope.loanGame = null;
        $scope.loanViewOpen = false;
        $scope.paymentViewOpen = false;
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
            $scope.balance.options.ceil = (parseFloat($rootScope.currentUser.shownBalance) >= $scope.loan.cost ? $scope.loan.cost : parseFloat($rootScope.currentUser.shownBalance));
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

        $scope.loan.balancePart = $scope.balance.value;
        $scope.loan.cardPart = $scope.loan.cost - $scope.balance.value;
        if ($scope.loan.cardPart > 0 && $scope.cardSelected == null) {
            notif.danger("Por favor seleccione una forma de pago");
            failedValidation = true;
        } else {
            $scope.loan.cardReference = $scope.cardSelected.card_reference;
        }

        if (!failedValidation) {
            sweet.default("Se enviará una solicitud de prestamo al propietario del juego", function() {
                rest("loan/requestGame").post($scope.loan, function(data) {
                    $rootScope.currentUser = data;
                    notif.success("Solicitud enviada con éxito");

                    if ($scope.loan.saveChanges) {
                        $rootScope.currentUser.billingAddress = $scope.loan.gamerAddress;
                        $rootScope.currentUser.geolocation = $scope.loan.gamerGeolocation;
                        $rootScope.currentUser.receiver = $scope.loan.gamerReceiver;
                        rest("publicUser/save").post($rootScope.currentUser, function(data) {
                            $rootScope.currentUser = data;
                        });
                    }

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

    $scope.viewOwnerRating = function(owner) {
        $state.go("^.publicProfile", {id: owner.id, alias: friendlyUrl(owner.name + ' ' + owner.lastName.substring(0, 1))});
    }

    $scope.getWeeklyCost = function(game) {
        if ($rootScope.settings != null && game != null) {
            let type = $rootScope.settings[Const.settings.priceChartingGames].type;
            let priceChartingGM = parseFloat($rootScope.settings[Const.settings.priceChartingGames].value);
            let gameLoanPCH = parseFloat($rootScope.settings[Const.settings.weekShippingCost].value);

            if (type === 'percentage') {
                return (game.uploadPayment / gameLoanPCH);
            }

            if (type === 'number') {
                return (game.uploadPayment + priceChartingGM) / gameLoanPCH;
            }
        }

        return null;
    }

    $scope.$watch("loan.cost", function(newValue) {
        $scope.slider.value = 0.0;
    
        if (newValue != null && $rootScope.currentUser != null && $scope.loan != null && $scope.paymentViewOpen) {
            $scope.slider.options.ceil = (parseFloat($rootScope.currentUser.shownBalance) >= newValue ? newValue : parseFloat($rootScope.currentUser.shownBalance));
        }
    });

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

    $scope.weekSelected = function(){
        $scope.loan.shippningCost = $scope.shippingCost;
        $scope.loan.feeGameClub = (($scope.loanGame.cost * $scope.loan.weeks) + $scope.loan.shippningCost) * feeLoanGamerPercentage/100;
        $scope.loan.subtotal = ($scope.loanGame.cost * $scope.loan.weeks) + $scope.loan.shippningCost + $scope.loan.feeGameClub;
        $scope.loan.taxes = $scope.loan.subtotal * taxes;
        $scope.loan.cost = $scope.loan.subtotal + $scope.loan.taxes;
    }
});
