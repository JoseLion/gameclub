angular.module('ContactUs').config(function($stateProvider) {
	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'contactUs', {
		url: '/contact-us',
		templateUrl: 'js/modules/contactUs/view/contactUs.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'ContactUsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'ContactUs',
					files: ['js/modules/contactUs/controller/contactUsCtrl.js', 'js/modules/contactUs/style/contactUs.less', 'js/modules/contactUs/style/contactUs.responsive.less']
				}, {
					name: 'LevelapBlog',
					files: ['js/modules/levelapBlog/resources/blog.css', 'js/modules/levelapBlog/resources/mostSeen.js']
				}]);
			},

			blogsPreview: function(openRest) {
				return openRest("levelapBlog/findArticles").post({isMostSeen: true}, function(data) {
					return data;
				});
			}
		}
	});
});