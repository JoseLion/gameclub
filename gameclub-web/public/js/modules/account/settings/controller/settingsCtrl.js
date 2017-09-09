angular.module('Settings').controller('SettingsCtrl', function($scope, $rootScope, reviews, rest, sweet, notif, forEach) {
    reviews.$promise.then(function(data) {
        console.log("data.content:", data.content);
        $scope.reviews = data.content;
    });

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
