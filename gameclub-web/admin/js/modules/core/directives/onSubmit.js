angular.module("Core").directive('onSubmit', function(forEach, $animate, Const) {
	return {
		restrict: 'A',
		require: 'form',
		scope: {
			onSubmit: "&"
		},
		replace: false,
		link: function($scope, element, attrs, frm) {
			let animationClass = "animated shake";

			element.on("submit", function() {
				if (frm.$invalid) {
					let invalidElements = element.find(".ng-invalid");
					let tabs = angular.element(angular.element(invalidElements[0]).parents('div.tab-pane'));
					frm.$setSubmitted(false);

					if (tabs.length > 0) {
						if (tabs.length == 1) {
							let tabScope = angular.element(tabs[0]).scope();

							if (tabScope != null) {
								if (tabScope.tab != null) {
									tabScope.tab.select();
								}
							}
						} else {
							for (let i = tabs.length-1; i >= 0; i--) {
								let tab = tabs[i];
								let tabScope = angular.element(tab).scope();

								if (tabScope != null) {
									if (tabScope.tab != null) {
										tabScope.tab.active = true;
										tabScope.tab.select();
									}
								}
							}
						}
						
						focusFirst(invalidElements[0]);
					} else {
						focusFirst(invalidElements[0]);
					}

					$animate.removeClass(invalidElements, animationClass);

					$animate.addClass(invalidElements, animationClass).then(function(data) {
						setTimeout(function() {
							$animate.removeClass(invalidElements, animationClass);
						}, 0);
					});
				} else {
					$scope.onSubmit();
					frm.$setSubmitted(true);
				}
			});

			function focusFirst(elem) {
				if (elem) {
					if (elem.focus != null) {
						elem.focus();
					}

					if (elem.select != null) {
						elem.select();
					}
				}
			}
		}
	};
});