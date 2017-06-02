angular.module("Game").controller('BulkLoadCtrl', function($scope, $uibModalInstance, excelReader, forEach, rest, saveAs, notif) {
	$scope.$watch("bulkloadFile", function(newValue, oldValue) {
		console.log("newValue: ", newValue);
		if (newValue != null) {
			excelReader.read(newValue).then(function(data) {
				console.log("data: ", data);
				if (data.sheets['Juegos'] != null) {
					$scope.sheet = data.sheets['Juegos'];
				} else {
					forEach(data.sheets, function(sheet, key) {
						$scope.sheet = sheet;
						return 'break';
					});
				}
			});
		} else {
			$scope.sheet = null;
		}
	});

	$scope.process = function() {
		if ($scope.sheet != null) {
			$scope.isProcessing = true;

			rest("game/processGamesExcel").multipart({bulkloadFile: $scope.bulkloadFile}, function(data) {
				console.log("data: ", data);

				if (data.hasFormat) {

					$scope.excelReport = data;
					$scope.isProcessing = false;
					$scope.wasProcessed = true;
				} else {
					notif.danger("El archivo no tiene formato correcto");
					$scope.isProcessing = false;
				}
			}, function(error) {
				$scope.isProcessing = false;
			});
		} else {
			notif.danger("El archivo no es correcto o no tiene registros");
		}
	}

	$scope.cancel = function() {
		$uibModalInstance.dismiss();
	}

	$scope.downloadTemplate = function() {
		$scope.isDownloading = true;

		rest("game/downloadGamesTemplate").download(function(data) {
			saveAs(data, "application/vnd.ms-excel", "Carga de Juegos.xlsx");
			$scope.isDownloading = false;
		}, function(error) {
			$scope.isDownloading = false;
		});
	}

	$scope.back = function() {
		$scope.wasProcessed = false;
		$scope.excelReport = null;
	}
});