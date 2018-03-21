angular.module('Core').directive('paymentMethod', function(Const, notif, $rootScope, rest, forEach, sweet, $ocLazyLoad, addPaymentezCard, $cookies, Const) {
    $ocLazyLoad.load('js/modules/core/directives/paymentMethod/paymentMethod.less');
    
    return {
        restrict: 'E',
        templateUrl: 'js/modules/core/directives/paymentMethod/paymentMethod.html',
        replace: true,
        scope: {
			totalBalance: '=',
			paymentMethods: '=',
			balance: '=',
            totalToPay: '=',
            cardSelected: '='
		},
        link: function($scope, element, attrs, ctrl) {
            $scope.paymentez = {};

            if ($scope.cardsList == null) {
                $scope.cardsList = [];

                rest("paymentez/listCards", true).get(function(data) {
                    $scope.cardsList = data;
                    $scope.cardsLoaded = true;
                });
            }

            $scope.balance.options.onChange = function() {
                if($scope.cardSelected != null && $scope.totalToPay - $scope.balance.value == 0) {
                    $scope.cardSelected.isSelected = false;
                    $scope.cardSelected = null;
                }
            };

            $scope.chooseCard = function(card) {
                forEach($scope.cardsList, function(cc) {
                    if (card.card_reference == cc.card_reference) {
                        cc.isSelected = true;
                        $scope.cardSelected = cc;
                    } else {
                        cc.isSelected = false;
                    }
                });
            };

            $scope.addCard = function() {
                $scope.paymentez.url = addPaymentezCard();
                $scope.isAddingCard = true;
            }

            $scope.deleteCard = function(card) {
                sweet.default("Se eliminará la forma de pago permanentemente", function() {
                    rest("paymentez/deleteCard/:cardReference").delete({cardReference: card.card_reference}, function() {
                        let index = $scope.cardsList.indexOf(card);
                        $scope.cardsList.splice(index, 1);
                        notif.success("Forma de pago eliminada con éxito");
                        sweet.close();
                    }, function(error) {
                        sweet.close();
                    });
                });
            }

            $scope.cancelAddCard = function() {
                $scope.isAddingCard = false;
                $scope.paymentez = {};
            }

            window.addEventListener('message', (e) => {
                if (e.data == Const.cardMessage) {
                    if ($cookies.get(Const.cookies.cardSuccess)) {
                        rest("paymentez/listCards", true).get((data) => {
                            $scope.cardsList = data;
                            $scope.cancelAddCard();
                        });

                        $cookies.remove(Const.cookies.cardSuccess);
                        $cookies.remove(Const.cookies.cardError);
                    } else {
                        $scope.addCardError = $cookies.get(Const.cookies.cardError);
                        $scope.cancelAddCard();
                        $cookies.remove(Const.cookies.cardSuccess);
                        $cookies.remove(Const.cookies.cardError);
                    }
                }
            });
        }
    };
});
