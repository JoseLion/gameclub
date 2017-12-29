angular.module('WorkForUs').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider
	.state(prefix + 'workForUs', {
		url: '/work-for-us',
		params: {anchor: null},
		templateUrl: 'js/modules/workForUs/view/workForUs.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'Trabaja con GameClub',
			description: '¿Quieres ser parte del equipo de GameClub?. Escríbenos a GameClub ¡La única plataforma gamer del Ecuador! Alquila tus Juegos, Gana Dinero, Juega mas pagando menos. Share and Play',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
		},
		controller: 'WorkForUsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'WorkForUs',
					files: ['js/modules/workForUs/controller/workForUsCtrl.js', 'js/modules/workForUs/style/workForUs.less', 'js/modules/workForUs/style/workForUs.responsive.less']
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
