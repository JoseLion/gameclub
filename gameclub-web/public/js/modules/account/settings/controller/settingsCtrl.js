angular.module('Settings').controller('SettingsCtrl', function($scope, $rootScope, reviews, rest, sweet, notif, forEach) {
    reviews.$promise.then(function(data) {
        $scope.gamerAverage = data.gamerAverage * 100.0 / 5.0;
        $scope.lenderAverage = data.lenderAverage * 100.0 / 5.0;
        $scope.gamerReviews = data.gamerReviews;
        $scope.lenderReviews = data.lenderReviews;
        setPagedData(data.reviews);
    });

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
