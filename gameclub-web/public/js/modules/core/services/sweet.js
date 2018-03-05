angular.module("Core").factory('sweet', function(SweetAlert, Const, notif) {
	return {
		default: function(message, confirm, cancel) {
			SweetAlert.swal({
				title: "Estás seguro?",
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
				title: "Estás seguro que desea guardar la información?",
				text: Const.messages.confirmation,
				type: "info",
				showCancelButton: true,
				confirmButtonText: "Sí, guardar!",
				cancelButtonText: "No, cancelar!",
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

		custom: function(title, message, confirm, cancel) {
			SweetAlert.swal({
				title: title,
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

		close: function() {
			swal.close();
		},

		error: function(message) {
			SweetAlert.swal("Error", message, "error");
		},

		success: function() {
			notif.success(Const.messages.success);
		}
	};
});