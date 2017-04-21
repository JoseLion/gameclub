angular.module('Blog').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'blog', {
		url: '/blog',
		params: {
			idBlog: null
		},
		templateUrl: 'js/modules/blog/view/blog.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'BlogCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Blog',
					files: ['js/modules/blog/controller/blogCtrl.js']
				}]);
			},
			idBlog: function($stateParams) {
				return $stateParams.idBlog;
			}
		}
	});

});
