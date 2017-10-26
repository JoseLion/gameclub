angular.module('ShippingKit').controller('ShippingKitCtrl', function($rootScope, $scope, notif, Const, $state, $filter) {

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
            step: 1,
            precision: 0,
            floor: 0
            //,
            // onChange: function() {
            //calculateTotalToPay();
            // }
        }
    };
    calculateTotalToPay();

    function calculateTotalToPay() {
        $scope.totalToPay = $scope.quantity.value * 12.5;
        $scope.balance.value = 0;
        $scope.balance.options.ceil = ($scope.totalToPay > $rootScope.currentUser.shownBalance) ? $rootScope.currentUser.shownBalance : $scope.totalToPay;
    }

});
