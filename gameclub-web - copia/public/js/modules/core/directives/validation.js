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
									notif.danger(label[0].textContent.toUpperCase() + ": " + Const.errorMessages[key]);
								}
							});

							return ctrl.$valid;
						}
					}

					notif.danger(Const.errorMessages[key]);
				}

				return ctrl.$valid;
			}


			/*let toValidate = element.parents('.to-validate');
			let helpBlock = angular.element('<small class="text-danger"></small>');
			let form = element.parents("form");

			if (form.length == 0) {
				console.error("The validation directive must be used within a form element. No form parent could be found!", form);
				return;
			}

			if (toValidate.length == 0) {
				toValidate = element.parent();
			}

			$scope.getAttrs = function() {
				return {
					required: attrs.required
				}
			};

			$scope.$watch($scope.getAttrs, function(newValue, oldValue) {
				if(newValue.required) {
					toValidate.addClass('label-required');
				} else {
					toValidate.removeClass('label-required');
				}
			}, true);

			$scope.$watch(function() {return ctrl.$modelValue;}, function(newValue, oldValue) {
				validateModel();
			});

			element.on('blur', function() {
				ctrl.$setDirty();
				ctrl.$setTouched();
				validateModel();
			});

			element.on('focus', function() {
				if (ctrl.$untouched) {
					ctrl.$setTouched();
				}

				if (ctrl.$pristine) {
					ctrl.$setDirty();
				}
			});

			form.on("submit", function() {
				if (ctrl.$untouched) {
					ctrl.$setTouched();
				}

				if (ctrl.$pristine) {
					ctrl.$setDirty();
				}

				let isValid = validateModel();

				if (isValid) {
					ctrl.$setUntouched();
					ctrl.$setPristine();
				}
			});

			element.on("$destroy", function() {
				helpBlock.remove();
			});

			function validateModel() {
				if (ctrl.$invalid && ctrl.$dirty && ctrl.$touched) {
					toValidate.addClass("has-error");

					if (element.parent().hasClass("input-group")) {
						element.parent().parent().append(helpBlock);
					} else {
						element.parent().append(helpBlock);
					}

					helpBlock.text(getErrorText());
					helpBlock.show();
				} else {
					if (toValidate.hasClass("has-error")) {
						toValidate.removeClass("has-error");
					}

					if (helpBlock.is(':visible')) {
						helpBlock.text('');
						helpBlock.hide();
					}
				}

				return ctrl.$valid;
			}

			function getErrorText() {
				let text = "";

				forEach(ctrl.$error, function(invalid, key) {
					if (invalid) {
						text += Const.errorMessages[key] + ", ";
					}
				});

				return text.substring(0, text.length - 2);
			}*/
		}
	};
});
