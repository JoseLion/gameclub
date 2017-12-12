angular.module("Gameclub", [
	'ui.router',
	'oc.lazyLoad',
	'ui.bootstrap',
	'ui.bootstrap.datetimepicker',
	'ngIdle',
	'ngSanitize',
	'ngResource',
	'ngCookies',
	'cgNotify',
	'ui.select',
	'oitozero.ngSweetAlert',
	'datatables',
	'datatables.buttons',
	'LevelapBlogAdmin',
	'Core',
	'Login',
	'Profile',
	'AdminUser',
	'Console',
	'Category',
	'Game',
	'PublicUser',
	'Avatar',
	'Faq',
	'Logistic',
	'ShippingPrice',
	'Settings',
	'Fine',
	'AmountRequest',
	'UserGames',
	'Reports'
]).config(["$locationProvider" ,"$httpProvider", function($locationProvider, $httpProvider, $http) {
	$httpProvider.defaults.withCredentials = true;

	$httpProvider.interceptors.push(["$rootScope", "$q", "$location", "$cookies", "Const", function($rootScope, $q, $location, $cookies, Const) {
		return {
			request: function(config) {
				if ($cookies.get(Const.cookieToken)) {
					config.headers["X-" + Const.cookieToken] = $cookies.get(Const.cookieToken);
					config.headers['Content-Encoding'] = 'gzip';
				}
				return config;
			}
		};
	}]);
}]).run(function(DTDefaultOptions, uibPaginationConfig, uiDatetimePickerConfig, uibDatepickerPopupConfig) {
	DTDefaultOptions.setLanguageSource('js/dataTableLanguage.json');

	uibPaginationConfig.firstText='Primero';
	uibPaginationConfig.previousText='Anterior';
	uibPaginationConfig.nextText='Siguiente';
	uibPaginationConfig.lastText='Último';

	uiDatetimePickerConfig.buttonBar.now.text='Ahora';
	uiDatetimePickerConfig.buttonBar.today.text='Hoy';
	uiDatetimePickerConfig.buttonBar.clear.text='Limpiar';
	uiDatetimePickerConfig.buttonBar.date.text='Fecha';
	uiDatetimePickerConfig.buttonBar.time.text='Hora';
	uiDatetimePickerConfig.buttonBar.close.text='Listo';

	uibDatepickerPopupConfig.clearText='Limpiar';
	uibDatepickerPopupConfig.closeText='Listo';
	uibDatepickerPopupConfig.currentText='Hoy';
}).service('urlRestPath', function($location) {
	let port = "8090";

	if ($location.$$port == 443) {
		port = "8390";
	}
	
	return {url: $location.$$protocol + "://" + $location.$$host + ":" + port + "/gameclub"};
});
