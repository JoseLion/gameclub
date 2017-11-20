angular.module("Core").filter("trusted", function($sce) {
	return function(url) {
		return $sce.trustAsResourceUrl(url);
	}
});