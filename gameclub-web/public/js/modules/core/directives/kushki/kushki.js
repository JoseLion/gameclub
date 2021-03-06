angular.module('Core').directive('kushki', function($window, $ocLazyLoad, Const, sweet, notif, $rootScope, rest) {
    $ocLazyLoad.load(['https://p1.kushkipagos.com/kushki/kushki/kushki.min.js', 'js/modules/core/directives/kushki/kushki.less']);

    return {
        restrict: 'E',
        templateUrl: 'js/modules/core/directives/kushki/kushki.html',
        replace: true,
        link: function($scope, element, attrs, ctrl) {
            $scope.ctrlDown = false;
            $scope.ctrlKey = 17;
            $scope.cmdKey = 91;
            $scope.cKey = 67;
            $scope.vKey = 86;
            $scope.xKey = 88;

            angular.element(element).bind("keyup", function($event) {
                $scope.ctrlDown = false;
            });

            angular.element(element).bind("keydown", function($event) {
                if ($event.keyCode == $scope.ctrlKey || $event.keyCode == $scope.cmdKey) $scope.ctrlDown = true;
            });

            angular.element(element).bind("keydown", function($event) {
                if ($scope.ctrlDown && ($event.keyCode == $scope.vKey || $event.keyCode == $scope.cKey || $event.keyCode == $scope.xKey)) return false;
            });

            $scope.kushkiSubmit = function() {
                let valid = true;
                if($scope.kushki == null || $scope.kushki.cardCVC == null) {
                    notif.danger('Se requiere el código de seguridad');
                    valid = false;
                }
                if($scope.kushki == null || $scope.kushki.cardExpiry == null) {
                    notif.danger('Se requiere la fecha de expiración');
                    valid = false;
                }
                if($scope.kushki == null || $scope.kushki.cardNumber == null) {
                    notif.danger('Se requiere el número de tarjeta');
                    valid = false;
                }
                if($scope.kushki == null || $scope.kushki.cardLastName == null) {
                    notif.danger('Se requiere el apellido(s)');
                    valid = false;
                }
                if($scope.kushki == null || $scope.kushki.cardFirstName == null) {
                    notif.danger('Se requiere el nombre(s)');
                    valid = false;
                }

                if(valid) {
                    sweet.save(function() {
                        var kushki = new Kushki({merchantId: Const.kushki.publicMerchantId, inTestEnvironment: Const.kushki.isTest});
                        let expiry = $scope.kushki.cardExpiry.split('/');
                        var cardData = {
                            name: $scope.kushki.cardFirstName.toUpperCase() + " " + $scope.kushki.cardLastName.toUpperCase(),
                            number: $scope.kushki.cardNumber.replace(/-/g, ''),
                            cvc: $scope.kushki.cardCVC,
                            expiryMonth: expiry[0],
                            expiryYear: expiry[1]
                        };

                        var kushkiCallback = function (response) {
                            if(response.token != null) {
                                let subscription = {
                                    subscriptionId: response.token,
                                    firstName: $scope.kushki.cardFirstName,
                                    lastName: $scope.kushki.cardLastName,
                                    email: $rootScope.currentUser.username,
                                    cardFinale: cardData.number.substr(cardData.number.length - 4, 4)
                                }

                                rest("publicUser/addKushkiSubscription").post(subscription, function(data) {
                                    $rootScope.currentUser = data;
                                    notif.success(Const.messages.success);
                                    sweet.close();
                                    $scope.kushki = {};
                                }, function(error) {
                                    sweet.close();
                                });
                            } else {
                                notif.danger(Const.messages.kushkiError);
                                sweet.close();
                            }
                        };
                        
                        kushki.requestSubscriptionToken({card: cardData}, kushkiCallback);
                    });
                }
            };

        }
    };
});
