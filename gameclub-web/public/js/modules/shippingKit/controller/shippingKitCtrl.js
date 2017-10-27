angular.module('ShippingKit').controller('ShippingKitCtrl', function($rootScope, $scope, notif, Const, $state, $filter, forEach, sweet, rest, $state) {

    if($rootScope.currentUser == null) {
        notif.danger(Const.messages.expired);
        $state.go('gameclub.home');
    }

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
            notif.danger(Const.errorMessages.cardRequired);
            return;
        }
        notif.closeAll();
        sweet.default("Se confirmará el envío de tu Welcome Kit", function() {
            let kit = {
                amountBalance: $scope.balance.value,
                amountCard: $scope.totalToPay - $scope.balance.value,
                paymentId: $scope.cardSelected.id
            };
            rest("welcomeKit/requestShippingKit").post(kit, function(data) {
                $rootScope.currentUser = data;
                $state.go('gameclub.account');
            }, function(error) {
                sweet.close();
            });
            sweet.close();
        });
    };

    function calculateTotalToPay() {
        $scope.totalToPay = $scope.quantity.value * 12.5;
        $scope.balance.value = 0;
        $scope.balance.options.ceil = ($scope.totalToPay > $rootScope.currentUser.shownBalance) ? $rootScope.currentUser.shownBalance : $scope.totalToPay;
    }

});
