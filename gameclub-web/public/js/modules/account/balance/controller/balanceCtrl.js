angular.module('Balance').controller('BalanceCtrl', function($scope, $rootScope, transactions, rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath,) {
	transactions.$promise.then(function(data){
		$scope.transactions = data;
	});

	$scope.takeYourBalance = function() {
		if (!$rootScope.currentUser.hasRuc) {
			SweetAlert.swal({
				title: "¿TIENES RUC?",
				text: "Si posees RUC es obligatorio registrarlo para poder\n" +
					"canjear tu SALDO. Realizaremos una verificación ante el SRI\n" +
					"antes de validar el pago.\n" +
					"Si tienes RUC, ingrésalo en TU PERFIL.",
				type: "warning",
				showCancelButton: true,
				confirmButtonText: "SI TENGO RUC",
				cancelButtonText: "NO TENGO RUC",
				closeOnConfirm: false,
				closeOnCancel: false,
				showLoaderOnConfirm: true
			}, function(isConfirm) {
				if (isConfirm) {
					if(!$rootScope.currentUser.hasRuc) {
						$state.go('gameclub.account.profile').then(function() {
							$location.hash('ruc-section');
							$anchorScroll();
						}); 
						swal.close();
					}
				} else {
					swal.close();
				}
			});
		} else {
			takeYourBalance();
		}
	}

	function takeYourBalance(){
        let modal = $uibModal.open({
			size: "md",
			backdrop: true,
			templateUrl: "takeYourBalance.html",
			controller: function($scope, $uibModalInstance, mostPlayed) {
				mostPlayed.$promise.then(function(data) {
					$scope.mostPlayed = data;
				});

				$scope.getPreviousGame = function() {
					let temp = $scope.mostPlayed.splice(0, 1);
					$scope.mostPlayed[3] = temp[0];
				}

				$scope.getNextGame = function() {
					let temp = $scope.mostPlayed.splice(-1, 1);
					$scope.mostPlayed.unshift(temp[0]);
				}

				$scope.cancel = function() {
					$uibModalInstance.dismiss();
				}

				$scope.ok = function() {
					$uibModalInstance.close();
				}
			},
			resolve: {
				mostPlayed: function(openRest) {
					return openRest("game/findMostPlayed", true).post();
				}
			}
		});

		modal.result.then(function() {
			SweetAlert.swal({
				title: "SE TE ENVIARÁ TU SALDO VÍA TRANSFERENCIA BANCARIA.",
				type: "warning",
				showCancelButton: true,
				confirmButtonText: "INGRESA TUS DATOS",
				cancelButtonText: "CANCELAR RETIRO",
				closeOnConfirm: false,
				closeOnCancel: false,
				showLoaderOnConfirm: true
			}, function(isConfirm) {
				if (isConfirm) {
					$state.go("gameclub.amountRequest");
				} else {
					swal.close();
				}
			});
		});
    }
});