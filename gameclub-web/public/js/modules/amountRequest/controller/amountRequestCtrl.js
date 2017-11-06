angular.module('AmountRequest').controller('AmountRequestCtrl', function($scope, $rootScope, provinces, notif, sweet, rest, SweetAlert, $state) {
    $scope.request = {};
    $scope.file = {};

    provinces.$promise.then(function(data) {
        $scope.provinces = data;
    });

    $scope.openFileBrowser = function(id) {
        setTimeout(function() {
            angular.element('#' + id).trigger('click');
        }, 0);
    }

    $scope.requestBalance = function() {
        if ($rootScope.currentUser.hasRuc && !$scope.rucOpened) {
            $scope.rucOpened = true;
        } else {
            sweet.default("Se enviará tu solicitud de retiro de saldo", function() {
                let formData = {
                    request: $scope.request,
                    identityPhoto: $scope.file.identityPhoto
                };

                if ($scope.file.bill != null) {
                    formData.billPhoto = $scope.file.bill;
                }

                rest("amountRequest/requestBalance").multipart(formData, function(data) {
                    $rootScope.currentUser = data;
                    SweetAlert.swal({
                        title: "SOLICITUD DE RETIRO DE SALDO EXITOSO",
                        text: "En los próximos días procederemos a validar toda la información que adjuntaste para generar el pago de tu saldo. Un asesor comercial se comunicará contigo para dar seguimiento a este proceso.",
                        type: "warning",
                        showCancelButton: false,
                        confirmButtonText: "OK",
                        closeOnConfirm: false,
                        closeOnCancel: false,
                        showLoaderOnConfirm: false
                    }, function() {
                        $state.go("gameclub.account.balance");
                        swal.close();
                    });
                }, function(error) {
                    swal.close();
                });
            });
        }
    }
});
