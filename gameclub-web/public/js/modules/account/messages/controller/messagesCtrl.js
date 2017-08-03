angular.module("Messages").controller('MessagesCtrl', function($scope, forEach, $filter) {
	$scope.messages = [];

	for (let i = 0; i < 30; i++) {
		$scope.messages.push({
			read: i == 0 ? false : true,
			selected: false,
			isPromo: i == 0 ? true : false
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

	$scope.getMessageDate = function(date) {
		date = new Date("08/03/2017 09:33:00");

		let today = new Date();

		if (angular.copy(date).setHours(0, 0, 0, 0) == angular.copy(today).setHours(0, 0, 0, 0)) {
			return $filter('date')(date, 'HH:mm');
		}

		

		return '';
	}
});