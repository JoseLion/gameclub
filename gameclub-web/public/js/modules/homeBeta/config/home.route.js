angular.module('Home').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'home', {
		url: '/home',
		params: {
			anchor: null
		},
		templateUrl: 'js/modules/homeBeta/view/home.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'HomeCtrl',
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
            }
		}
	});

});
