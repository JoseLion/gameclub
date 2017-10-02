angular.module('Settings').controller('SettingsCtrl', function($scope, $rootScope, reviews, rest, sweet, notif, forEach) {
    reviews.$promise.then(function(data) {
        $scope.gamerAverage = data.gamerAverage * 100.0 / 5.0;
        $scope.lenderAverage = data.lenderAverage * 100.0 / 5.0;
        $scope.gamerReviews = data.gamerReviews;
        $scope.lenderReviews = data.lenderReviews;
        setPagedData(data.reviews);
    });
    $scope.numberCards = false;
    $scope.card = true;
    console.log($rootScope.currentUser.paymentMethods);
    if($rootScope.currentUser.paymentMethods.length>0){
        console.log($rootScope.currentUser.paymentMethods);
        var j = 0;
        for(i = 0; i < $rootScope.currentUser.paymentMethods.length; i++){
            j++;
            if (j == 2) {
                $scope.numberCards = true;
                $scope.card = false;
            }
            // j++;
        }
    } else{
        $scope.numberCards = false;
        $scope.card = true;
    }

    $scope.loadMore = function() {
        $scope.page++;

        rest("review/getReviewsOfCurrentUser/:page").get({page: $scope.page}, function(data) {
            setPagedData(data.reviews);
        });
    }

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
        $state.go('gameclub.account.settings');
    }

    function setPagedData(data) {
        if ($scope.reviews == null) {
            $scope.reviews = [];
        }

        angular.extend($scope.reviews, data.content);
        $scope.isLastPage = data.last;
        $scope.page = data.number;
    }
});
