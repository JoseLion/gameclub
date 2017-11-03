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
			killTimer();

			if (!message.isLoan) {
				rest("message/getMessageById/:messageId").get({messageId: message.id}, function(data) {
					if(message.isFine) {
						$scope.fine = data;
					} else if(message.isAmountRequest) {
						$scope.amountRequest = data;
					} else {
						$scope.kit = data;
					}
					canvasToBottom();
					if (!message.read) {
						message.read = true;
						$rootScope.currentUser.unreadMessages--;
					}
				});
			}

			if (message.isLoan) {
				rest("message/getLoanMessage/:messageId").get({messageId: message.id}, function(data) {
					$scope.loan = data;
					$scope.loan.isDisabled = true;

					if ($rootScope.currentUser.id != $scope.loan.gamer.id) {
						$scope.loan.lenderAddress = $scope.loan.lenderAddress != null ? $scope.loan.lenderAddress : $rootScope.currentUser.billingAddress;
						$scope.loan.lenderGeolocation = $scope.loan.lenderGeolocation != null ? $scope.loan.lenderGeolocation : $rootScope.currentUser.geolocation;
						$scope.loan.lenderReceiver = $scope.loan.lenderReceiver != null ? $scope.loan.lenderReceiver : $rootScope.currentUser.receiver;

						if ($scope.loan.restore != null) {
							$scope.loan.restore.isDisabled = true;
							$scope.loan.restore.lenderAddress = $scope.loan.restore.lenderAddress != null ? $scope.loan.restore.lenderAddress : $rootScope.currentUser.billingAddress;
							$scope.loan.restore.lenderGeolocation = $scope.loan.restore.lenderGeolocation != null ? $scope.loan.restore.lenderGeolocation : $rootScope.currentUser.geolocation;
							$scope.loan.restore.lenderReceiver = $scope.loan.restore.lenderReceiver != null ? $scope.loan.restore.lenderReceiver : $rootScope.currentUser.receiver;
						}
					}

					if ($rootScope.currentUser.id == $scope.loan.gamer.id) {
						$scope.loan.gamerGeolocation = $scope.loan.gamerGeolocation != null ? $scope.loan.gamerGeolocation : $rootScope.currentUser.geolocation;

						if ($scope.loan.restore != null) {
							$scope.loan.restore.isDisabled = true;
							$scope.loan.restore.gamerAddress = $scope.loan.restore.gamerAddress != null ? $scope.loan.restore.gamerAddress : $rootScope.currentUser.billingAddress;
							$scope.loan.restore.gamerGeolocation = $scope.loan.restore.gamerGeolocation != null ? $scope.loan.restore.gamerGeolocation : $rootScope.currentUser.geolocation;
							$scope.loan.restore.gamerReceiver = $scope.loan.restore.gamerReceiver != null ? $scope.loan.restore.gamerReceiver : $rootScope.currentUser.receiver;
						}
					}

					canvasToBottom();
					startTimer(new Date($scope.loan.returnDate));

					if (!message.read) {
						message.read = true;
						$rootScope.currentUser.unreadMessages--;
					}
				});
			}

			$scope.fine = {};
			if(message.subject == "Cobro multa" && message.fromUser == null && message.toUser == null){
				rest("message/findFineMessage/:messageId").get({messageId: message.id}, function(data) {
					$scope.fine = data;
					if ($scope.fine.message != null) {
						canvasToBottom();

						if (!message.read) {
							message.read = true;
							$rootScope.currentUser.unreadMessages--;
						}
					}
				});
			}

			$scope.amountRequest = {};
			if(message.subject == "Retiro saldo" && message.fromUser == null && message.toUser == null){
				rest("message/amountRqFineMessage/:messageId").get({messageId: message.id}, function(data) {
					$scope.amountRequest = data;
					if ($scope.amountRequest.message != null) {
						canvasToBottom();

						if (!message.read) {
							message.read = true;
							$rootScope.currentUser.unreadMessages--;
						}
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

	$scope.confirmKit = function(kit) {
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
		if (kit.geolocation == null) {
			notif.danger("La geolocalización es obligatoria");
			isValid = false;
		}

		if (isValid) {
			if(kit.quantity === 0) {
				sweet.default("Se confirmará el envío de tu Welcome Kit", function() {
					rest("welcomeKit/confirmWelcomeKit").post(kit, function(data) {
						$scope.kit = data;
						sweet.close();
					}, function(error) {
						sweet.close();
					});
				});
			} else {
				sweet.default("Se confirmará el envío de tu Shipping Kit", function() {
					rest("welcomeKit/confirmShippingKit").post(kit, function(data) {
						$scope.kit = data;
						sweet.close();
					}, function(error) {
						sweet.close();
					});
				});
			}
		}
	};

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
				canvasToBottom();
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
				$scope.loan.lenderAddress = $rootScope.currentUser.billingAddress;
				$scope.loan.lenderGeolocation = $rootScope.currentUser.geolocation;
				$scope.loan.lenderReceiver = $rootScope.currentUser.receiver;
				sweet.close();
				canvasToBottom();
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
				canvasToBottom();
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
					$scope.loan.isDisabled = true;
					notif.success("Préstamo confirmado");
					sweet.close();
					canvasToBottom();
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

	$scope.confirmGamer = function() {
		let isValid = true;

		if ($scope.loan.gamerAddress == null || $scope.loan.gamerAddress == '') {
			notif.danger("El campo dirección es requerido para continuar");
			isValid = false;
		}

		if ($scope.loan.gamerGeolocation == null) {
			notif.danger("La geolocalización es requerida para continuar");
			isValid = false;
		}

		if ($scope.loan.gamerReceiver == null || $scope.loan.gamerReceiver == '') {
			notif.danger("El campo Persona de Entrega es requerido para continuar");
			isValid = false;
		}

		if (isValid) {
			sweet.default("Se realizará el pago del préstamo", function() {
				$scope.loan.isDisabled = true;

				rest("loan/confirmGamer").post($scope.loan, function(data) {
					$scope.loan = data;
					$rootScope.currentUser.shownBalance -= data.balancePart;
					$scope.loan.isDisabled = true;
					notif.success("Pago realizado con éxito");
					sweet.close();
					canvasToBottom();
				});

				if ($scope.loan.saveChanges == true) {
					$rootScope.currentUser.billingAddress = $scope.loan.gamerAddress;
					$rootScope.currentUser.geolocation = $scope.loan.gamerGeolocation;
					$rootScope.currentUser.receiver = $scope.loan.gamerReceiver;

					rest("publicUser/save").post($rootScope.currentUser, function(data) {
						$rootScope.currentUser = data;
					});
				}
			});
		}
	}

	$scope.getDatePlus = function(millis, days) {
		let date = new Date(millis);
		date.setDate(date.getDate() + days);
		return date;
	}

	$scope.gamerConfirmRestore = function() {
		let isValid = true;

		if ($scope.loan.restore.gamerAddress == null || $scope.loan.restore.gamerAddress == '') {
			notif.danger("El campo dirección es requerido para continuar");
			isValid = false;
		}

		if ($scope.loan.restore.gamerGeolocation == null) {
			notif.danger("La geolocalización es requerida para continuar");
			isValid = false;
		}

		if ($scope.loan.restore.gamerReceiver == null || $scope.loan.restore.gamerReceiver == '') {
			notif.danger("El campo Persona de Entrega es requerido para continuar");
			isValid = false;
		}

		if (isValid) {
			sweet.default("Se confirmará la dirección de retiro", function() {
				$scope.loan.restore.isDisabled = true;

				rest("restore/confirmGamer").post($scope.loan.restore, function(data) {
					$scope.loan = data;
					$scope.loan.restore.isDisabled = true;
					notif.success("Dirección de retiro confirmada");
					sweet.close();
					canvasToBottom();
				}, function(error) {
					sweet.close();
				});

				if ($scope.loan.restore.saveChanges == true) {
					$rootScope.currentUser.billingAddress = $scope.loan.restore.gamerAddress;
					$rootScope.currentUser.geolocation = $scope.loan.restore.gamerGeolocation;
					$rootScope.currentUser.receiver = $scope.loan.restore.gamerReceiver;

					rest("publicUser/save").post($rootScope.currentUser, function(data) {
						$rootScope.currentUser = data;
					}, function(error) {
						sweet.close();
					});
				}
			});
		}
	}

	$scope.lenderConfirmRestore = function() {
		let isValid = true;

		if ($scope.loan.restore.lenderAddress == null || $scope.loan.restore.lenderAddress == '') {
			notif.danger("El campo dirección es requerido para continuar");
			isValid = false;
		}

		if ($scope.loan.restore.lenderGeolocation == null) {
			notif.danger("La geolocalización es requerida para continuar");
			isValid = false;
		}

		if ($scope.loan.restore.lenderReceiver == null || $scope.loan.restore.lenderReceiver == '') {
			notif.danger("El campo Persona de Entrega es requerido para continuar");
			isValid = false;
		}

		if (isValid) {
			sweet.default("Se confirmará la dirección de entrega", function() {
				$scope.loan.restore.isDisabled = true;

				rest("restore/confirmLender").post($scope.loan.restore, function(data) {
					$scope.loan = data;
					console.log($scope.loan);
					//$rootScope.currentUser.shownBalance += $scope.loan.;
					$scope.loan.restore.isDisabled = true;
					notif.success("Dirección de entrega confirmada");
					sweet.close();
					canvasToBottom();
				}, function(error) {
					sweet.close();
				});

				if ($scope.loan.restore.saveChanges == true) {
					$rootScope.currentUser.billingAddress = $scope.loan.restore.lenderAddress;
					$rootScope.currentUser.geolocation = $scope.loan.restore.lenderGeolocation;
					$rootScope.currentUser.receiver = $scope.loan.restore.lenderReceiver;

					rest("publicUser/save").post($rootScope.currentUser, function(data) {
						$rootScope.currentUser = data;
					}, function(error) {
						sweet.close();
					});
				}
			});
		}
	}

	$scope.sendReview = function() {
		let isValid = true;

		if ($scope.loan.review == null) {
			isValid = false;
		} else {
			if ($scope.loan.gamer.id == $rootScope.currentUser.id) {
				if ($scope.loan.review.lenderScore == null || $scope.loan.review.lenderScore < 1) {
					notif.danger("Debes dar una calificación para continuar");
					isValid = false;
				}

				if ($scope.loan.review.lenderComment == null || $scope.loan.review.lenderComment == '') {
					notif.danger("Debes dejar un comentario para continuar");
					isValid = false;
				}
			} else {
				if ($scope.loan.review.gamerScore == null || $scope.loan.review.gamerScore < 1) {
					notif.danger("Debes dar una calificación para continuar");
					isValid = false;
				}

				if ($scope.loan.review.gamerComment == null || $scope.loan.review.gamerComment == '') {
					notif.danger("Debes dejar un comentario para continuar");
					isValid = false;
				}
			}
		}

		if (isValid) {
			sweet.default("Se enviará tu calificación y comentario", function() {
				$scope.loan.review.loan = {id: $scope.loan.id};

				rest("review/sendReview").post($scope.loan.review, function(data) {
					$scope.loan = data;
					notif.success("Calificación enviada con éxito");
					sweet.close();
					canvasToBottom();
				}, function(error) {
					sweet.close();
				});
			});
		}
	}

	/*$scope.acceptReview = function(id) {
		sweet.default("El review aparecerá en tu perfíl público", function() {
			rest("review/acceptReview/:id").get({id: id}, function(data) {
				$scope.loan = data;
				notif.success("Se ha aceptado el review con éxito");
				sweet.close();
				canvasToBottom();
			}, function(error) {
				sweet.close();
			});
		});
	}*/

	$scope.showLendDay = function(acceptedDate) {
		let lendDay = new Date(acceptedDate);
		switch (lendDay.getDay()) {
			case 5:
				lendDay.setDate(lendDay.getDate() + 3);
				break;
			case 6:
				lendDay.setDate(lendDay.getDate() + 2);
				break;
			default:
				lendDay.setDate(lendDay.getDate() + 1);
				break;
		}
		return lendDay;
	};

	function clearCanvas() {
		$scope.kit = null;
		$scope.fine = null;
		$scope.loan = null;
	}

	function canvasToBottom(canvas, i) {
		canvas = canvas != null ? canvas : angular.element(".canvas");
		let height = canvas[0].scrollHeight - canvas[0].offsetHeight;
		let pixels = 10;
		let time = 500;

		if (i == null) {
			setTimeout(function() {
				canvasToBottom(null, pixels);
			}, 10);
		} else {
			setTimeout(function() {
				if (i < height) {
					canvas[0].scrollTop = i;
					i += pixels;
					canvasToBottom(canvas, i);
				}
			}, Math.round(pixels*time/height));
		}
	}

	function startTimer(finish) {
		$scope.timer = {};
		let today = new Date();
		let diff = finish.getTime() - today.getTime();

		let days = diff / 1000 / 60 / 60 / 24;
		$scope.timer.days = Math.floor(days);

		if (days % 1 > 0) {
			let hours = (days % 1) * 24;
			$scope.timer.hours = Math.floor(hours);

			if (hours % 1 > 0) {
				let minutes = (hours % 1) * 60;
				$scope.timer.mins = Math.floor(minutes);

				if (minutes % 1 > 0) {
					let seconds = (minutes % 1) * 60;
					$scope.timer.secs = Math.round(seconds);
				} else {
					$scope.timer.secs = 0;
				}
			} else {
				$scope.timer.mins = 0;
				$scope.timer.secs = 0;
			}
		} else {
			$scope.timer.hours = 0;
			$scope.timer.mins = 0;
			$scope.timer.secs = 0;
		}

		$scope.timerInterval = setInterval(function() {
			$scope.$apply(function() {
				if ($scope.timer.secs > 0) {
					$scope.timer.secs--;
				} else {
					if ($scope.timer.mins > 0) {
						$scope.timer.mins--;
						$scope.timer.secs = 59;
					} else {
						if ($scope.timer.hours > 0) {
							$scope.timer.hours--;
							$scope.timer.mins = 59;
							$scope.timer.secs = 59;
						} else {
							if ($scope.timer.days > 0) {
								$scope.timer.days--;
								$scope.timer.hours = 23;
								$scope.timer.mins = 59;
								$scope.timer.secs = 59;
							} else {
								killTimer();
							}
						}
					}
				}
			});
		}, 1000);
	}

	function killTimer() {
		if ($scope.timer != null) {
			clearInterval($scope.timerInterval);
			$scope.timer.days = 0;
			$scope.timer.hours = 0;
			$scope.timer.mins = 0;
			$scope.timer.secs = 0;
		}
	}
});
