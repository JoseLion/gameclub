angular.module('WorkForUs').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'workForUs', {
		url: '/work-for-us',
		params: {
			anchor: null
		},
		templateUrl: 'js/modules/workForUs/view/workForUs.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'WorkForUsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'WorkForUs',
					files: ['js/modules/workForUs/controller/workForUsCtrl.js']
				}, {
					name: 'LevelapBlog',
					files: ['js/modules/levelapBlog/resources/blog.css', 'js/modules/levelapBlog/resources/mostSeen.js']
				}]);
			},

			anchor: function($stateParams) {
				return $stateParams.anchor;
			},

			blogsPreview: function(openRest) {
				return openRest("levelapBlog/findArticles").post({isMostSeen: true}, function(data) {
					return data;
				});
			}
		}
	});

});
