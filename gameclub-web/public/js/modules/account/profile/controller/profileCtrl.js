angular.module('Profile').controller('ProfileCtrl', function($scope) {

    $scope.contactInfo = {};
    $scope.contactMean = {fb:'Pablo Ponce'};

    $scope.isEditContact = false;
    $scope.editContact = function() {
        $scope.isEditContact = !$scope.isEditContact;
        if($scope.isEditContact) {
            $scope.contactInfoTmp = angular.copy($scope.contactInfo);
        } else {
            $scope.contactInfo = angular.copy($scope.contactInfoTmp);
        }
    };

    $scope.isEditContactMean = false;
    $scope.editContactMean = function() {
        $scope.isEditContactMean = !$scope.isEditContactMean;
        if($scope.isEditContactMean) {
            $scope.contactMeanTmp = angular.copy($scope.contactMean);
        } else {
            $scope.contactMean = angular.copy($scope.contactMeanTmp);
        }
    };

    $scope.games = [1];


});
