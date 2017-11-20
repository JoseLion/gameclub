angular.module('Core').directive('paymentMethod', function(Const, notif, $rootScope, rest, forEach, sweet, $ocLazyLoad, addPaymentezCard) {
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
                addPaymentezCard();
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
        }
    };
});
