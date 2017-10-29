angular.module('AmountRequest').controller('AmountRequestCtrl', function($rootScope, $scope) {

    $scope.amountRequest = {};

    $scope.provinces = [
        {
            name: 'Provincia 1'
        }, {
            name: 'Provincia 2'
        }, {
            name: 'Provincia 3'
        }, {
            name: 'Provincia 4'
        }, {
            name: 'Provincia 5'
        }
    ];
    $scope.cities = [
        {
            name: 'Ciudad 1'
        }, {
            name: 'Ciudad 2'
        }, {
            name: 'Ciudad 3'
        }, {
            name: 'Ciudad 4'
        }, {
            name: 'Ciudad 5'
        }
    ];
    $scope.addressInformation = {
        province: null,
        city: null
    };

});
