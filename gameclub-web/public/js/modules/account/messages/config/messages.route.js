angular.module('Messages').config(function($stateProvider) {
	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'messages', {
		url: '/messages',
		templateUrl: 'js/modules/account/messages/view/messages.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: 'Mensajes de Usuario GameClub',
			description: 'Alquilar tu juegos es facil con GameClub! Entra a tu perfil para empezar a ganar dinero.',
			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer'
		},
		controller: 'MessagesCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Messages',
					files: ['js/modules/account/messages/controller/messagesCtrl.js', 'js/modules/account/messages/style/messages.less', 'js/modules/account/messages/style/messages.responsive.less']
				}]);
			},

			messages: function(rest) {
				return rest("message/findMessages").post();
			}
		}
	});
});
