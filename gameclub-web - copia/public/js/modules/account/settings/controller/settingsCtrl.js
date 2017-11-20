angular.module('Settings').controller('SettingsCtrl', function($scope, $rootScope, reviews, addCardError, cardsList, rest, sweet, notif, forEach, Const, $location, $cookies) {
    /*rest("paymentez/deleteCard/:cardReference").delete({cardReference: "12680100941731713551"}, function(data) {
        console.log("data: ", data);
    });*/








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
        console.log("Card List: ", data);
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
        let today = new Date();
        let token = "application_code=" + Const.paymentez.appCode +
                    "&email=" + encodeURIComponent($rootScope.currentUser.username) +
                    "&failure_url=" + encodeURIComponent($location.$$protocol + "://" + $location.$$host + "/gameclub/account/settings") +
                    "&response_type=redirect" +
                    "&session_id=" + $cookies.get(Const.cookieToken) +
                    "&success_url=" + encodeURIComponent($location.$$protocol + "://" + $location.$$host + "/gameclub/account/settings") +
                    "&uid=" + $rootScope.currentUser.id +
                    "&" + today.getTime() +
                    "&" + Const.paymentez.appKey;

        let url = Const.paymentez.baseUrl + "/api/cc/add/?" +
                "application_code=" + Const.paymentez.appCode +
                "&uid=" + $rootScope.currentUser.id +
                "&email=" + encodeURIComponent($rootScope.currentUser.username) +
                "&session_id=" + $cookies.get(Const.cookieToken) +
                "&auth_timestamp=" + today.getTime() +
                "&auth_token=" + sha256(token) +
                "&response_type=redirect" +
                "&success_url=" + encodeURIComponent($location.$$protocol + "://" + $location.$$host + "/gameclub/account/settings") +
                "&failure_url=" + encodeURIComponent($location.$$protocol + "://" + $location.$$host + "/gameclub/account/settings") +
                "&buyer_phone=0998591484";

        window.open(url, "_self");
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
