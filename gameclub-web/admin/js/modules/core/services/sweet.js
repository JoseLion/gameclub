angular.module("Core").factory('sweet', function(SweetAlert, Const, notif) {
	return {
		default: function(message, confirm, cancel) {
			SweetAlert.swal({
				title: Const.messages.sure,
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
				title: Const.messages.sure,
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

		changeStatus: function(confirm, cancel) {
			SweetAlert.swal({
				title: Const.messages.sure,
				text: "Se cambiará el estado del registro",
				type: "warning",
				showCancelButton: true,
				confirmButtonText: "¡Sí, continuar!",
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

		error: function(message) {
			SweetAlert.swal("Error", message, "error");
		},

		close: function() {
			swal.close();
		},

		success: function() {
			notif.success(Const.messages.success);
		},

		statusChanged: function() {
			notif.success(Const.messages.statusChanged);
		}
	};
});
