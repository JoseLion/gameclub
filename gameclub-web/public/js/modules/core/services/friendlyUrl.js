angular.module("Core").factory('friendlyUrl', function() {
	return function(url) {
		url = url.toLowerCase();
		url = url.replace(/\s/g, '-');
		url = url.replace("ñ", "ni");
		url = url.replace(/[áàâä]/g, "a");
		url = url.replace(/[éèêë]/g, "e");
		url = url.replace(/[íìîï]/g, "i");
		url = url.replace(/[óòôö]/g, "o");
		url = url.replace(/[úùûü]/g, "u");
		url = url.replace(/[#@!¡%^&()|~=`{}\[\]:";'<>?¿,.\/\\]/g, "");

		return url;
	}
});