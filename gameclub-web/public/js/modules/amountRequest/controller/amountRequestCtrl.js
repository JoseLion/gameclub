angular.module('AmountRequest').controller('AmountRequestCtrl', function($scope, $rootScope, provinces, notif, sweet, rest, SweetAlert, $state) {
    $scope.request = {};
    $scope.file = {};

    provinces.$promise.then(function(data) {
        $scope.provinces = data;
    });

    $scope.openFileBrowser = function() {
        setTimeout(function() {
            $scope.$apply(function() {
                angular.element('#input-file').trigger('click');
            });
        }, 0);
    }

    $scope.requestBalance = function() {
        let isValid = true;

        if ($scope.request.accountFullName == null || $scope.request.accountFullName == '') {
            isValid = false;
            notif.danger("El nombre del 1er beneficiario es obligatorio")
        }

        if ($scope.request.accountDocument == null || $scope.request.accountDocument == '') {
            isValid = false;
            notif.danger("La cédula de identidad es obligatoria")
        }

        if ($scope.request.accountBank == null || $scope.request.accountBank == '') {
            isValid = false;
            notif.danger("El banco es obligatorio")
        }

        if ($scope.request.accountNumber == null || $scope.request.accountNumber == '') {
            isValid = false;
            notif.danger("el número de cuenta es obligatorio")
        }

        if ($scope.request.accountEmail == null || $scope.request.accountEmail == '') {
            isValid = false;
            notif.danger("El correo electrónico es obligatorio")
        }

        if ($scope.file.identityPhoto == null) {
            isValid = false;
            notif.danger("La foto de tu cédula es obligatoria")
        }

        if (!$rootScope.currentUser.hasRuc) {
            if ($scope.request.province == null) {
                isValid = false;
                notif.danger("La provicia es obligatoria")
            }

            if ($scope.request.locationDestiny == null) {
                isValid = false;
                notif.danger("La ciudad es obligatoria")
            }

            if ($scope.request.address == null || $scope.request.address == '') {
                isValid = false;
                notif.danger("La dirección de entrega es obligatoria")
            }

            if ($scope.request.contactPhone == null || $scope.request.contactPhone == '') {
                isValid = false;
                notif.danger("El teléfono de contacto es obligatorio")
            }
        }

        if (isValid) {
            sweet.default("Se enviará una solicitud de retiro de saldo", function() {
                let formData = {
                    request: $scope.request,
                    identityPhoto: $scope.file.identityPhoto
                };

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
                });
            });
        }
    }
});
