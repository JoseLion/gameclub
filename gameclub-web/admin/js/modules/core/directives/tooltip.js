angular.module("Core").directive('tooltip', function(){
	return {
		restrict: 'A',
		link: function($scope, element, attrs) {
			let container = angular.element('<div class="levelap-tooltip"></div>');
			let helper = angular.element('<span></span>');
			container.insertBefore(element);
			container.append(element);
			console.log("text: ", attrs);
			helper.text(attrs.tooltip);

			if (attrs.tooltipPosition == "left") {
				helper.insertBefore(element);
			} else {
				helper.insertAfter(element);
			}

			$scope.getComplexSize = function() {
				return {
					elementWidth: element[0].offsetWidth,
					elementHeight: element[0].offsetHeight,
					helperWidth: helper[0].offsetWidth,
					helperHeight: helper[0].offsetHeight,
					disabled: attrs.disabled
				};
			}

			$scope.$watch($scope.getComplexSize, function(newValue, oldValue) {
				setHelperPosition(newValue);
				if(newValue.disabled) {
					helper.css("visibility", "hidden");
				}
			}, true);

			element.on("mouseenter", function(event) {
				helper.css("visibility", "visible");
			});

			element.on("mouseleave", function(event) {
				helper.css("visibility", "hidden");
			});

			element.on("resize", function(event) {
				$scope.$apply();
			});

			function setHelperPosition(pos) {
				let margin;

				switch(attrs.tooltipPosition) {
					case "left":
						helper.addClass("levelap-tooltip-left");

						if (container.parents("td").length > 0) {
							helper.css("padding", "5px");
							margin = "-" + (pos.helperWidth * 1.05) + "px";
							helper.css("margin-left", margin)
						} else {
							margin = "-" + (pos.helperWidth + 5.0) + "px";
							helper.css("margin-left", margin);
						}

						margin = "-" + ((pos.helperHeight / 2.0) - (pos.elementHeight / 2.0)) + "px";
						helper.css("margin-top", margin);

					break;

					case "right":
						helper.addClass("levelap-tooltip-right");
						margin = "-" + ((pos.helperHeight / 2.0) - (pos.elementHeight / 2.0)) + "px";
						helper.css("margin-top", margin);
					break;

					case "bottom":
						helper.addClass("levelap-tooltip-bottom");
						margin = "-" + ((pos.elementWidth / 2.0) + (pos.helperWidth / 2.0)) + "px";
						helper.css("margin-left", margin);
					break;

					default:
						helper.addClass("levelap-tooltip-top");
						margin = "-" + ((pos.elementWidth / 2.0) + (pos.helperWidth / 2.0)) + "px";
						helper.css("margin-left", margin);
					break;
				}
			}
		}
	};
});
