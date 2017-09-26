angular.module('Settings').controller('SettingsCtrl', function($scope, $rootScope, rest, sweet, notif) {
    console.log("Entra: "+$rootScope.currentUser.paymentMethods.length);
    $scope.validCards = [];
    angular.forEach($rootScope.currentUser.paymentMethods, function(cards){
                if (cards.status) {
                    $scope.validCards.push(cards);
                }
            });
    console.log("Cards: "+$scope.validCards.length);
    $scope.removeMethod = function(method) {
        sweet.default("Se eliminará la forma de pago permanentemente", function() {
            rest("publicUser/deletePaymentMethod/:subscriptionId").get({subscriptionId: method.id}, function(data) {
                $rootScope.validCards = data;
                notif.success("Forma de pago eliminada con éxito");
                sweet.close();
                // $state.go("^.account.settings");

            }, function(error) {
                sweet.close();
            });
        });
        $state.go('gameclub.account.settings');
    }
});
