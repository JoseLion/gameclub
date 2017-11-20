angular.module("Core").directive('stars', function() {
	return {
		restrict: 'E',
		templateUrl: 'js/modules/core/directives/stars.html',
		require: 'ngModel',
		scope: {
			ngModel: '=',
			solid: '@?'
		},
		link: function($scope, element, attrs, ctrl) {
			$scope.stars = [{val: 0}, {val: 0}, {val: 0}, {val: 0}, {val: 0}];

			$scope.$watch('ngModel', function(newValue, oldValue) {
				if (newValue != null && newValue > 0) {
					let fulls = Math.floor(newValue / 20.0);
					let partial = ((newValue / 20.0) % 1) * 100;

					let allDone = false;
					for(let i = 0; i < 5; i++) {
						if (!allDone) {
							if (fulls > i) {
								$scope.stars[i].val = 100;
							} else {
								$scope.stars[i].val = partial;
								allDone = true;
							}
						} else {
							$scope.stars[i].val = 0;
						}
					}
				}
			});

			$scope.getGradient = function(star) {
				let style = {};

				switch ($scope.solid) {
					case 'light':
						if (star != null && star.val != null && star.val > 0) {
							style = {'background-image': '-webkit-linear-gradient(left, white ' + star.val + '%, #071428 0%)'};
						} else {
							style = {'background-color': '#071428'};
						}

						break;

					case 'dark':
						if (star != null && star.val != null && star.val > 0) {
							style = {'background-image': '-webkit-linear-gradient(left, #2CB4BF ' + star.val + '%, #1C3664 0%)'};
						} else {
							style = {'background-color': '#1C3664'};
						}

						break;

					case 'red':
						if (star != null && star.val != null && star.val > 0) {
							style = {'background-image': '-webkit-linear-gradient(left, #E43345 ' + star.val + '%, #071428 0%)'};
						} else {
							style = {'background-color': '#071428'};
						}

						break;

					case 'blue':
						if (star != null && star.val != null && star.val > 0) {
							style = {'background-image': '-webkit-linear-gradient(left, #2CB4BF ' + star.val + '%, #071428 0%)'};
						} else {
							style = {'background-color': '#071428'};
						}

						break;

					default:
						if (star != null && star.val != null && star.val > 0) {
							style = {'background-image': '-webkit-linear-gradient(left, transparent ' + star.val + '%, #2e3e57 0%), -webkit-linear-gradient(#2CB4BF 50%, #E43345 70%)'};
						} else {
							style = {'background-color': '#2e3e57'};
						}

						break;
				}

				return style;
			}
		}
	};
});