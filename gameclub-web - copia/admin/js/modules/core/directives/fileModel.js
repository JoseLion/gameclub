angular.module("Core").directive('fileModel', function(Const) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function($scope, element, attrs, ctrl) {
			let toValidate = element.parents('.to-validate');
			let helpBlock = angular.element('<small class="text-danger"></small>');
			element.parent().parent().append(helpBlock);

			element.on('change', function() {
				$scope.$apply(function() {
					if (element[0].files != null) {
						if ('multiple' in attrs) {
							ctrl.$setViewValue(element[0].files);
						} else {
							ctrl.$setViewValue(element[0].files.item(0));
						}
					} else {
						ctrl.$setViewValue(null);
					}
				});
			});

			$scope.$watch('ngModel', function(newValue, oldValue) {
				if (newValue == null) {
					element.val(null);
					if($scope.isRequired) {
						toValidate.addClass('has-error');
					}
				} else {
					if($scope.isRequired) {
						toValidate.removeClass('has-error');
					}
				}
				validateModel();
			});

			$scope.getAttrs = function() {
				return {
					required: attrs.required
				}
			};

			$scope.$watch($scope.getAttrs, function(newValue, oldValue) {
				$scope.isRequired = newValue.required;
				if(newValue.required) {
					if(element.val() == null || element.val() == '') {
						toValidate.addClass('has-error');
					}
				} else {
					toValidate.removeClass('has-error');
				}
				validateModel();
			}, true);

			function validateModel() {
				if (toValidate.hasClass("has-error")) {
					helpBlock.text(Const.errorMessages.required);
					helpBlock.show();
				} else {
					if (helpBlock.is(':visible')) {
						helpBlock.text('');
						helpBlock.hide();
					}
				}
			}

		}
	};
});
