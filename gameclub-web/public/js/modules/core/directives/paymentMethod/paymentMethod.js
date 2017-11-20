angular.module('Core').directive('paymentMethod', function(Const, notif, $rootScope, rest, forEach, sweet, $ocLazyLoad) {
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
            $scope.balance.options.onChange = function() {
                if($scope.cardSelected != null && $scope.totalToPay - $scope.balance.value == 0) {
                    $scope.cardSelected.isSelected = false;
                    $scope.cardSelected = null;
                }
            };
            $scope.chooseCard = function(paymentMethod) {
                forEach($scope.paymentMethods, function(value, key) {
                    if(paymentMethod.id == value.id) {
                        value.isSelected = true;
                        $scope.cardSelected = value;
                    } else {
                        value.isSelected = false;
                    }
                });
            };

            $scope.removeCard = function(paymentMethod) {
                sweet.default('Esta acción eliminara la tarjeta asociada', function() {
                    rest("publicUser/deletePaymentMethod/:subscriptionId").get({subscriptionId: paymentMethod.id}, function(data) {
                        $rootScope.currentUser = data;
                        notif.success(Const.messages.success);
                        sweet.close();
                    });
                });
            }

        }
    };
});
