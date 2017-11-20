angular.module('ShippingKit').controller('ShippingKitCtrl', function($rootScope, $scope, notif, Const, $state, $filter, forEach, sweet, rest, $state, shippingKitValue) {

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

    shippingKitValue.$promise.then(function(data) {
        $scope.shippingKitValue = parseFloat(data.value);
        calculateTotalToPay();
    });

    $scope.amountBalance = 0;
    $scope.amountCard = 0;

    $scope.completePayment = function() {
        if(($scope.totalToPay - $scope.balance.value > 0) && $scope.cardSelected == null) {
            notif.danger(Const.errorMessages.cardRequired);
            return;
        }
        notif.closeAll();
        sweet.default("Se confirmará el envío de tu Shipping Kit", function() {
            let kit = {
                quantity: $scope.quantity.value,
                amountBalance: $scope.balance.value,
                amountCard: $scope.totalToPay - $scope.balance.value
            };
            if($scope.cardSelected != null) {
                kit.paymentId = $scope.cardSelected.id;
            }
            rest("welcomeKit/requestShippingKit").post(kit, function(data) {
                $rootScope.currentUser = data;
                $state.go('gameclub.account.myGames');
            }, function(error) {
                sweet.close();
            });
            sweet.close();
        });
    };

    function calculateTotalToPay() {
        $scope.totalToPay = $scope.quantity.value * $scope.shippingKitValue;
        $scope.balance.value = 0;
        $scope.balance.options.ceil = ($scope.totalToPay > $rootScope.currentUser.shownBalance) ? $rootScope.currentUser.shownBalance : $scope.totalToPay;
    }

});
