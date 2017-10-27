angular.module('ShippingKit').controller('ShippingKitCtrl', function($rootScope, $scope, notif, Const, $state, $filter, forEach) {

    if($rootScope.currentUser == null) {
        notif.danger(Const.messages.expired);
        $state.go('gameclub.home');
    }
    console.log($rootScope.currentUser.paymentMethods)

    $scope.quantity = {
        value: 1,
        options: {
            hidePointerLabels: true,
            hideLimitLabels: true,
            showSelectionBar: true,
            step: 1,
            precision: 0,
            floor: 1,
            ceil: 10,
            onChange: function() {
                calculateTotalToPay();
            }
        }
    };
    $scope.balance = {
        value: 0,
        options: {
            hidePointerLabels: true,
            hideLimitLabels: true,
            showSelectionBar: true,
            step: 1/100,
            precision: 1/100,
            floor: 0
        }
    };
    $scope.amountBalance = 0;
    $scope.amountCard = 0;
    calculateTotalToPay();

    $scope.completePayment = function() {
        if(($scope.totalToPay - $scope.balance.value > 0) && $scope.cardSelected == null) {
            console.log('DEBE SELECCIONAR');
            return;
        }
        console.log('SUBMIT DE FORM');
    };

    function calculateTotalToPay() {
        $scope.totalToPay = $scope.quantity.value * 12.5;
        $scope.balance.value = 0;
        $scope.balance.options.ceil = ($scope.totalToPay > $rootScope.currentUser.shownBalance) ? $rootScope.currentUser.shownBalance : $scope.totalToPay;
    }

});
