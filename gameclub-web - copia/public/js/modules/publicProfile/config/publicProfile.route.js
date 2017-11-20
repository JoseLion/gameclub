angular.module('PublicProfile').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider

	.state(prefix + 'publicProfile', {
		url: '/publicProfile/:id/:alias',
		params: {id: null, alias: null},	
		templateUrl: 'js/modules/publicProfile/view/publicProfile.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'PublicProfileCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'PublicProfile',
					files: ['js/modules/publicProfile/controller/publicProfileCtrl.js', 'js/modules/publicProfile/style/publicProfile.less', 'js/modules/publicProfile/style/publicProfile.responsive.less']
				}]);
			},

			user: function(rest, $stateParams) {
				return rest("publicUser/findOne/:id").get({id: $stateParams.id});
			},

			reviews: function(rest, $stateParams) {
				return rest("review/getReviewsOfUser/:id/:page").get({id: $stateParams.id, page: 0});
			}
		}
	});

});
