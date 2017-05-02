angular.module("Core").factory('sweet', function(SweetAlert, Const) {
	return {
		default: function(message, confirm, cancel) {
			SweetAlert.swal({
				title: message,
				text: message,
				type: "info",
				showCancelButton: true,
				confirmButtonText: "Si",
				cancelButtonText: "No",
				closeOnConfirm: false,
				closeOnCancel: false,
				showLoaderOnConfirm: true
			},
			function(isConfirm) {
				if (isConfirm) {
					confirm();
				} else {
					if (cancel == null) {
						swal.close();
					} else {
						cancel();
					}
				}
			});
		},

		save: function(confirm, cancel) {
			SweetAlert.swal({
				title: "¿Está seguro que desea guardar la información?",
				text: Const.messages.confirmation,
				type: "info",
				showCancelButton: true,
				confirmButtonText: "¡Sí, guardar!",
				cancelButtonText: "¡No, cancelar!",
				closeOnConfirm: false,
				closeOnCancel: false,
				showLoaderOnConfirm: true
			},
			function(isConfirm) {
				if (isConfirm) {
					confirm();
				} else {
					if (cancel == null) {
						swal.close();
					} else {
						cancel();
					}
				}
			});
		},

		close: function() {
			swal.close();
		},

		error: function(message) {
			SweetAlert.swal("Error", message, "error");
		}
	};
});