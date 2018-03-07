angular.module("Settings").controller('CardRedirectCtrl', function(error, $cookies, Const) {
	$cookies.put(Const.cookies.cardSuccess, error == null);
	$cookies.put(Const.cookies.cardError, error || '');

	window.parent.postMessage(Const.cardMessage, "*");
});