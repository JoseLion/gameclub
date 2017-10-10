angular.module('Referred').controller('ReferredCtrl', function($scope, $rootScope, rest, sweet, notif, forEach) {
	

	$scope.transactions=[{date:"18/JUN/2017",transaction:"ALQUILASTE", game: "CALL OF DUTY II", time: 3, creditbalance: 13, debitbalance:0, debitcard:0 },
        {date:"12/JUN/2017",transaction:"ALQUILASTE", game: "ZELDA BIW", time: 1, creditbalance: 3.50, debitbalance:0, debitcard:0 },
        {date:"09/JUN/2017",transaction:"JUGASTE", game: "GTA V", time: 2, creditbalance: 0, debitbalance:1, debitcard:2},
        {date:"26/MAY/2017",transaction:"JUGASTE", game: "MARIO ODYSSEY", time: 1, creditbalance: 0, debitbalance:3, debitcard:0 },
        {date:"12/JUN/2017",transaction:"ALQUILASTE", game: "INJUSTICE 2", time: 1, creditbalance: 2.50, debitbalance:0, debitcard:0 }];

});