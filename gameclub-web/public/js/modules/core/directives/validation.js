angular.module('Core').directive('validation', function(Const, forEach, notif) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function($scope, element, attrs, ctrl) {
			let form = element.parents("form");

			if (form.length == 0) {
				console.error("The validation directive must be used within a form element. No form parent could be found!", form);
				return;
			}

			element.on('blur', function() {
				ctrl.$setDirty();
			});

			element.on('focus', function() {
				if (ctrl.$pristine) {
					ctrl.$setDirty();
				}
			});

			form.on("submit", function() {
				if (ctrl.$pristine) {
					ctrl.$setDirty();
				}

				if (isModelValid()) {
					ctrl.$setPristine();
				}
			});

			function isModelValid() {
				if (ctrl.$invalid && ctrl.$dirty) {
					let validGroup = angular.element(element.parents('.valid-group')[0]);

					if (validGroup != null) {
						let label = validGroup.find('label');

						if (label != null) {
							forEach(ctrl.$error, function(invalid, key) {
								if (invalid) {
									notif.danger(label[0] ? label[0].textContent.toUpperCase() + ": " + Const.errorMessages[key] : Const.errorMessages[key]);
								}
							});

							return ctrl.$valid;
						}
					}

					notif.danger(Const.errorMessages[key]);
				}

				return ctrl.$valid;
			}
		}
	};
});
