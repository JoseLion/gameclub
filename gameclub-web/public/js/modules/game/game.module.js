angular.module('Game', ['ui.router', 'ui.router.metatags']);
function runBlock($rootScope, MetaTags) {
	$rootScope.MetaTags = MetaTags;
}