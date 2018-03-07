angular.module('Settings').controller('SettingsCtrl', function($scope, $rootScope, $cookies, reviews, cardsList, rest, sweet, notif, forEach, addPaymentezCard, Const) {
    $scope.paymentez = {};

    reviews.$promise.then(function(data) {
        $scope.gamerAverage = data.gamerAverage * 100.0 / 5.0;
        $scope.lenderAverage = data.lenderAverage * 100.0 / 5.0;
        $scope.gamerReviews = data.gamerReviews;
        $scope.lenderReviews = data.lenderReviews;

        setPagedData(data.reviews);
    });

    cardsList.$promise.then(function(data) {
        $scope.cardsList = data;
    });

    $scope.numberCards = false;
    $scope.card = true;

    if ($scope.cardsList != null && $scope.cardsList.length > 0){
        var j = 0;
        for(i = 0; i < $scope.cardsList.length; i++){
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
        $scope.paymentez.url = addPaymentezCard();
        $scope.isAddingCard = true;
    }

    $scope.cancelAddCard = function() {
        $scope.isAddingCard = false;
        $scope.paymentez = {};
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

    window.addEventListener('message', (e) => {
        if (e.data == Const.cardMessage) {
            if ($cookies.get(Const.cookies.cardSuccess)) {
                rest("paymentez/listCards", true).get((data) => {
                    $scope.cardsList = data;
                    $scope.cancelAddCard();
                });

                $cookies.remove(Const.cookies.cardSuccess);
                $cookies.remove(Const.cookies.cardError);
            } else {
                $scope.addCardError = $cookies.get(Const.cookies.cardError);
                $scope.cancelAddCard();
                $cookies.remove(Const.cookies.cardSuccess);
                $cookies.remove(Const.cookies.cardError);
            }
        }
    });

    function setPagedData(data) {
        if ($scope.reviews == null) {
            $scope.reviews = [];
        }

        angular.extend($scope.reviews, data.content);
        $scope.isLastPage = data.last;
        $scope.page = data.number;
    }
});