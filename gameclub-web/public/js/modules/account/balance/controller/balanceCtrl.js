angular.module('Balance').controller('BalanceCtrl', function($scope, $rootScope, rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath, openReport) {

	$scope.transactions = [];
	$scope.idUser=0;
	rest("transaction/lastFiveTransactions").get(function(data) {
		$scope.transactions = data.content;
		if(data.content.length>0 ){
			var i=0;
			console.log(data.content);
			for (i=0;i< data.content.length;i++) { 
			   	$scope.idUser=data.content[i].owner.id;
			   	break;
			}
		}
	});

	$scope.takeYourBalance = function() {
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
					goToProfileRuc();
					swal.close();
				} else {
					takeYourBalance();
					swal.close();
				}
			} else {
				if ($rootScope.currentUser.hasRuc) {
					goToProfileRuc();
					swal.close();
				} else {
					takeYourBalance();
					swal.close();
				}
			}
		});
	};

	$scope.viewHistorial = function() {
		openReport.excel('transactions', {owner: $rootScope.currentUser.id});
	};

	function goToProfileRuc() {
		$state.go('gameclub.account.profile').then(function() {
			$location.hash('ruc-section');
			$anchorScroll();
		});
	}

	function takeYourBalance() {
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

				$scope.$on("$stateChangeSuccess", function(event, toState, toParams, fromState, fromParams, options) {
					$uibModalInstance.dismiss();
				});
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
					swal.close();
				} else {
					swal.close();
				}
			});
		});
    }
});
