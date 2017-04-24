angular.module('Home').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'home', {
		url: '/home',
		params: {
			anchor: null
		},
		templateUrl: 'js/modules/home/view/home.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'HomeCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Home',
					files: ['js/modules/home/controller/homeCtrl.js']
				}]);
			},

			anchor: function($stateParams) {
				return $stateParams.anchor;
			}
		}
	});

});
