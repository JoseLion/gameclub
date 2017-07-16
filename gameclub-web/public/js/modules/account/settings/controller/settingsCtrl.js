angular.module('Settings').controller('SettingsCtrl', function($scope, $rootScope, rest, sweet, notif) {
    $scope.removeMethod = function(method) {
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
});
