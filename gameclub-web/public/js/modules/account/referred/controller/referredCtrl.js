angular.module('Referred').controller('ReferredCtrl', function($scope, $rootScope, rest, sweet, notif, $location, Const) {
	$scope.reffer = {};
	let baseUrl = $location.$$protocol + '://' + $location.$$host + "/gameclub/login?token=";
	$scope.costReferred = parseFloat($rootScope.settings[Const.settings.costReferred].value);

	if ($rootScope.currentUser.urlToken) {
		$scope.reffer.link = baseUrl + $rootScope.currentUser.urlToken;
	}

	$scope.generateUrlToken = function() {
		sweet.default("Se generará un nuevo link para referir", function() {
			rest("publicUser/generateUrlToken").get(function(data) {
				$scope.reffer.link = baseUrl + data;
				notif.success("Link generado con éxito");
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	$scope.copyLinkToClipboard = function() {
		angular.element("#reffer-link")[0].select();

		try {
			let successful = document.execCommand('copy');
			if (!successful) {
				throw successful;
			}
		} catch (err) {
			window.prompt("Copy to clipboard: Ctrl+C, Enter", toCopy);
		}
	}

	$scope.shareLinkFB = function() {
		window.open("https://www.facebook.com/sharer/sharer.php?u=" + $scope.reffer.link, "Compartelo en Facebbok", "width=500,height=500");
	}
});
