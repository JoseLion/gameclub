angular.module('PublicProfile').controller('PublicProfileCtrl', function($scope, user, reviews, rest) {
	user.$promise.then(function(data) {
		$scope.user = data;
	});

	reviews.$promise.then(function(data) {
        $scope.gamerAverage = data.gamerAverage * 100.0 / 5.0;
        $scope.lenderAverage = data.lenderAverage * 100.0 / 5.0;
        $scope.gamerReviews = data.gamerReviews;
        $scope.lenderReviews = data.lenderReviews;
        setPagedData(data.reviews);
    });

    $scope.loadMore = function() {
        $scope.page++;

        rest("review/getReviewsOfUser/id:/:page").get({id: $scope.user.id, page: $scope.page}, function(data) {
            setPagedData(data.reviews);
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