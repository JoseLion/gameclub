angular.module('Settings').controller('SettingsCtrl', function($scope, $rootScope, rest, sweet, notif, Const) {

    loadExtraData();
    $rootScope.$watch('extraData', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            loadExtraData();
        }
    })
    function loadExtraData() {
        $scope.hasKushkiSubscription = false;
        $scope.kushkiHelpText = "Ingresar";
        if($rootScope.currentUser != null) {
            rest("publicUser/getExtraData/:id").get({id: $rootScope.currentUser.id}, function(response) {
                $scope.extraData = response.extra;
                $scope.hasKushkiSubscription = response.subscriptionActive;
                if($scope.hasKushkiSubscription) {
                    $scope.kushkiHelpText = "Actualizar";
                }
            });
        }
    }
    $scope.removeCard = function() {
        sweet.save(function() {
            rest("publicUser/removeKushkiSubscription/:id").get({id: $rootScope.currentUser.id}, function(data) {
                $rootScope.currentUser.kushkiSubscriptionActive = data.kushkiSubscriptionActive;
                delete $rootScope.extraData;
                $scope.hasKushkiSubscription = false;
                $scope.kushkiHelpText = "Ingresar";
                notif.success(Const.messages.success);
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
    };

});
