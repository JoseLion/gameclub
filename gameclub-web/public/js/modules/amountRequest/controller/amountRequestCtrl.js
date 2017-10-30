angular.module('AmountRequest').controller('AmountRequestCtrl', function($scope, provinces) {
    $scope.request = {};

    provinces.$promise.then(function(data) {
        $scope.provinces = data;
    });

    $scope.openFileBrowser = function() {
        angular.element('#input-file').trigger('click')
    }
    
    $scope.addressInformation = {
        province: null,
        city: null
    };

});
