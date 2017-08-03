angular.module("Messages").controller('MessagesCtrl', function($scope, messages, forEach, $filter, rest) {
	let page = 0;
	messages.$promise.then(function(data) {
		$scope.messages = data.content;
	});

	$scope.getFormattedDate = function(millis) {
		let date = new Date(millis);
		let today = new Date();

		if (angular.copy(date).setHours(0, 0, 0, 0) == angular.copy(today).setHours(0, 0, 0, 0)) {
			return $filter('date')(date, 'HH:mm');
		}

		if ($filter('date')(date, 'w') == $filter('date')(today, 'w')) {
			return $filter('date')(date, 'EEE');
		}

		return $filter('date')(date, " d 'de' LLLL");
	}

	$scope.messageSelected = function(message) {
		forEach($scope.messages, function(msg) {
			if (msg.selected) {
				msg.selected = false;
				return 'break';
			}
		});
		message.selected = true;

		rest("message/getWelcomeKitMessages/:messageId", true).get({messageId: message.id}, function(data) {
			$scope.welcomeKits = data;
			message.read = true;
		});
	}

	$scope.noSelected = function() {
		let selected = true;

		forEach($scope.messages, function(msg) {
			if (msg.selected) {
				selected = false;
				return 'break';
			}
		});

		return selected;
	}
});