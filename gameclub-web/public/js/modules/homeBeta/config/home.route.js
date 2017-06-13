angular.module('Beta').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'beta', {
		url: '/beta',
		params: {
			anchor: null
		},
		templateUrl: 'js/modules/homeBeta/view/home.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'HomeBetaCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Home',
					files: ['js/modules/homeBeta/controller/homeCtrl.js']
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
            },

            provinces: function(openRest) {
				return openRest("location/findChildrenOf/:code", true).get({code: 'EC'}, function(data) {
					return data;
				});
			}
		}
	});

});
