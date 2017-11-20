angular.module('Core').factory('notif', function(notify) {
	let url = "views/notify.html";

	notify.config({startTop:130});

	return {
		info: function(text) {
			return notify({
				message: text,
				classes: 'alert-info',
				templateUrl: url
			});
		},

		success: function(text) {
			return notify({
				message: text,
				classes: 'alert-success',
				templateUrl: url
			});
		},

		warning: function(text) {
			return notify({
				message: text,
				classes: 'alert-warning',
				templateUrl: url
			});
		},

		danger: function(text) {
			return notify({
				message: text,
				classes: 'alert-danger',
				templateUrl: url
			});
		},

		none: function(text) {
			return notify({
				message: text,
				classes: '',
				templateUrl: ''
			});
		},

		closeAll: function() {
			return notify.closeAll();
		}
	};
});
