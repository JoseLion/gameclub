angular.module("Messages").controller('MessagesCtrl', function($scope, $rootScope, messages, forEach, $filter, rest, geolocation, notif, sweet, $state, friendlyUrl) {
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
		if (!message.selected) {
			forEach($scope.messages, function(msg) {
				if (msg.selected) {
					msg.selected = false;
					return 'break';
				}
			});

			message.selected = true;
			clearCanvas();

			if (message.isLoan == false) {
				rest("message/getWelcomeKitMessages/:messageId", true).get({messageId: message.id}, function(data) {
					$scope.welcomeKits = data;
					
					if (!message.read) {
						message.read = true;
						$rootScope.currentUser.unreadMessages--;
					}
				});
			}

			if (message.isLoan == true) {
				rest("message/getLoanMessage/:messageId").get({messageId: message.id}, function(data) {
					$scope.loan = data;

					if ($rootScope.currentUser.id != $scope.loan.gamer.id) {
						$scope.loan.lenderAddress = $scope.loan.lenderAddress != null ? $scope.loan.lenderAddress : $rootScope.currentUser.billingAddress;
						$scope.loan.lenderGeolocation = $scope.loan.lenderGeolocation != null ? $scope.loan.lenderGeolocation : $rootScope.currentUser.geolocation;
						$scope.loan.lenderReceiver = $scope.loan.lenderReceiver != null ? $scope.loan.lenderReceiver : $rootScope.currentUser.receiver;
					}

					if (!message.read) {
						message.read = true;
						$rootScope.currentUser.unreadMessages--;
					}
				});
			}
		}
	}

	$scope.getFromTo = function(message) {
		if (!message.isLoan) {
			return 'GAME CLUB';
		} else {
			if (message.fromUser) {
				return message.fromUser.name + ' ' + $filter('limitTo')(message.fromUser.lastName, 1) + '.';
			} else {
				return message.toUser.name + ' ' + $filter('limitTo')(message.toUser.lastName, 1) + '.';
			}
		}
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

	$scope.openMap = function(obj, property) {
		geolocation().result.then(function(pos) {
			if (property == null) {
				obj.geolocation = {
					x: pos.lat,
					y: pos.lng
				};
			} else {
				obj[property] = {
					x: pos.lat,
					y: pos.lng
				};
			}
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
				rest("welcomeKit/confirmWelcomeKit").post(kit, function(data) {
					let index = $scope.welcomeKits.indexOf(kit);
					$scope.welcomeKits[index] = data;
					sweet.close();
				}, function(error) {
					sweet.close();
				});
			});
			
		}
	}

	$scope.goToGame = function(publicUserGame) {
		$state.go('gameclub.game', {id: publicUserGame.game.id, consoleId: publicUserGame.console.id, name: friendlyUrl(publicUserGame.game.name)});
	}

	$scope.goTouser = function(user) {
		$state.go('gameclub.publicProfile', {id: user.id, alias: friendlyUrl(user.name + ' ' + $filter('limitTo')(user.lastName, 1) + '.')});
	}

	$scope.cancelLoanRequest = function() {
		sweet.default("Se cancelara tu solicitud de préstamo", function() {
			rest("loan/cancelLoan/:id").get({id: $scope.loan.id}, function(data) {
				notif.success("Préstamo cancelado");
				$scope.loan = data;
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	$scope.acceptLoan = function() {
		sweet.default("Aceptarás el préstamo de este juego", function() {
			rest("loan/acceptLoan/:id").get({id: $scope.loan.id}, function(data) {
				notif.success("Préstamo aceptado");
				$scope.loan = data;
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	$scope.rejectLoan = function() {
		sweet.default("Rechazarás el préstamo de este juego", function() {
			rest("loan/rejectLoan/:id").get({id: $scope.loan.id}, function(data) {
				notif.success("Préstamo rechazado");
				$scope.loan = data;
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	}

	$scope.confirmLender = function() {
		let isValid = true;

		if ($scope.loan.lenderAddress == null || $scope.loan.lenderAddress == '') {
			notif.danger("El campo dirección es requerido para continuar");
			isValid = false;
		}

		if ($scope.loan.lenderGeolocation == null) {
			notif.danger("La geolocalización es requerida para continuar");
			isValid = false;
		}

		if ($scope.loan.lenderReceiver == null || $scope.loan.lenderReceiver == '') {
			notif.danger("El campo Persona de Entrega es requerido para continuar");
			isValid = false;
		}

		if ($scope.loan.boxNumber == null || $scope.loan.boxNumber == '') {
			notif.danger("El número de caja es requerido para continuar");
			isValid = false;
		}

		if (isValid) {
			sweet.default("Confirmaras el préstamo de forma definitiva", function() {
				$scope.loan.isDisabled = true;

				rest("loan/confirmLender").post($scope.loan, function(data) {
					$scope.loan = data;
					notif.success("Préstamo confirmado");
					sweet.close();
				});

				if ($scope.loan.saveChanges == true) {
					$rootScope.currentUser.billingAddress = $scope.loan.lenderAddress;
					$rootScope.currentUser.geolocation = $scope.loan.lenderGeolocation;
					$rootScope.currentUser.receiver = $scope.loan.lenderReceiver;

					rest("publicUser/save").post($rootScope.currentUser, function(data) {
						$rootScope.currentUser = data;
					});
				}
			});
		}
	}

	function clearCanvas() {
		$scope.welcomeKits = null;
		$scope.loan = null;
	}
});