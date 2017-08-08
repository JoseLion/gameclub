angular.module("Messages").controller('MessagesCtrl', function($scope, $rootScope, messages, forEach, $filter, rest, geolocation, notif, sweet) {
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

	$scope.openMap = function(obj) {
		geolocation().result.then(function(pos) {
			obj.geolocation = pos;
		});
	}

	$scope.confirmWelcomeKit = function(kit) {
		let isValid = true;

		if (kit.address == null || kit.address == '') {
			notif.danger("El campo dirrección es obligatorio");
			isValid = false;
		}

		if (kit.phone == null || kit.phone == '') {
			notif.danger("El campo teléfono es obligatorio");
			isValid = false;
		}

		if (kit.receiver == null || kit.receiver == '') {
			notif.danger("El campo persona de entrega es obligatorio");
			isValid = false;
		}

		if (isValid) {
			sweet.default("Se confirmará el envío de tu Welcome Kit", function() {
				let confirmObj = {
					kitId: kit.id,
					address: kit.address,
					phone: kit.phone,
					city: $rootScope.currentUser.location.other,
					receiver: kit.receiver
				};

				rest("message/confirmWelcomeKit").post(confirmObj, function(data) {
					console.log("data: ", data);
					sweet.close();
				}, function(error) {
					sweet.close();
				});
			});
			
		}
	}
});