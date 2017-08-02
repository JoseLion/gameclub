angular.module("Messages").controller('MessagesCtrl', function($scope, forEach) {
	$scope.messages = [];

	for (let i = 0; i < 30; i++) {
		$scope.messages.push({
			read: i == 0 ? false : true,
			selected: false
		});
	}

	$scope.messageSelected = function(message) {
		forEach($scope.messages, function(msg) {
			if (msg.selected) {
				msg.selected = false;
				return 'break';
			}
		});

		message.read = true;
		message.selected = true;
	}
});