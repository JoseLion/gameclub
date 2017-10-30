
angular.module('Balance').controller('BalanceCtrl', function($scope, $rootScope, rest, SweetAlert, $uibModal, $location, $anchorScroll, $state, Const, $http, urlRestPath) {

	if($rootScope.currentUser == null) {
		$state.go('gameclub.home');
	}

	$scope.transactions = [];
	console.log($rootScope.currentUser.id);
	rest("transaction/lastFiveTransactions").get(function(data) {
		console.log(data);
		$scope.transactions = data.content;
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
			     	// $state.go('^.profile', {anchor: 'ruc'})
			     	$state.go('gameclub.account.profile').then(function() {
			  			$location.hash('ruc-section');
			  			$anchorScroll();
			  		});
			    	swal.close();
			    }

			    if($rootScope.currentUser.hasRuc){
			    	swal.close();
			    	// mostGame();
		    	} else{
		       		swal.close();
			    	// mostGame();
		       }
		    }

		});
	}

	/*********** Lógica para el llamdo del mosal ****************/
	function mostGame(){
        let modal = $uibModal.open({
			size: "md",
			backdrop: true,
			templateUrl: "takeYourBalance.html",
			controller: function($scope, $uibModalInstance, notif, Const) {
				$scope.terms = {};
				$scope.isSaving = false;
				$scope.isFacebook = true;
				$scope.ok = function() {
					$scope.isSaving = true;
					if ($scope.terms.status) {
						$uibModalInstance.close();
					} else {
						$scope.isSaving = false;
						notif.warning(Const.messages.acceptTerms);
					}
				}

				$scope.cancel = function() {
					$uibModalInstance.dismiss();
				}
			},
			resolve: {}
		});
    }

});
