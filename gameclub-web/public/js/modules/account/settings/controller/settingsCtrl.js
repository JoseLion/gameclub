angular.module('Settings').controller('SettingsCtrl', function($scope, $rootScope, reviews, addCardError, cardsList, rest, sweet, notif, forEach, addPaymentezCard) {
    reviews.$promise.then(function(data) {
        $scope.gamerAverage = data.gamerAverage * 100.0 / 5.0;
        $scope.lenderAverage = data.lenderAverage * 100.0 / 5.0;
        $scope.gamerReviews = data.gamerReviews;
        $scope.lenderReviews = data.lenderReviews;
        setPagedData(data.reviews);
    });

    if (addCardError != null) {
        $scope.addCardError = addCardError;
    }

    cardsList.$promise.then(function(data) {
        console.log("data: ", data);
        $scope.cardsList = data;
    });

    $scope.numberCards = false;
    $scope.card = true;

    if ($rootScope.currentUser != null && $rootScope.currentUser.paymentMethods.length>0){
        var j = 0;
        for(i = 0; i < $rootScope.currentUser.paymentMethods.length; i++){
            j++;
            if (j == 2) {
                $scope.numberCards = true;
                $scope.card = false;
            }
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

    $scope.addCard = function() {
        addPaymentezCard();
    }

    $scope.removeCard = function(card) {
        sweet.default("Se eliminará la forma de pago permanentemente", function() {
            rest("paymentez/deleteCard/:cardReference").delete({cardReference: card.card_reference}, function() {
                let index = $scope.cardsList.indexOf(card);
                $scope.cardsList.splice(index, 1);
                notif.success("Forma de pago eliminada con éxito");
                sweet.close();
            }, function(error) {
                sweet.close();
            });
        });
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
