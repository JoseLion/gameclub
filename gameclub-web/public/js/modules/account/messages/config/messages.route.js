angular.module('Messages').config(function($stateProvider) {

	let prefix = 'gameclub.account.';

	$stateProvider
	.state(prefix + 'messages', {
		url: '/messages',
		templateUrl: 'js/modules/account/messages/view/messages.html',
		data: {displayName: 'GameClub', description: '', keywords: ''},
		controller: 'MessagesCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Messages',
					files: ['js/modules/account/messages/controller/messagesCtrl.js']
				}]);
			},

			messages: function(rest) {
				return rest("message/findMessages").post(function(data) {
					return data;
				});
			}
		}
	});
});