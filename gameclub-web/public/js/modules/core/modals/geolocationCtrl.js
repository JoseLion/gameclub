angular.module("Core").controller('GeolocationCtrl', function($scope, $uibModalInstance, NgMap, $http, Const) {
	$scope.currentPos = {lat: 0, lng: 0};

	NgMap.getMap().then(function(map) {
		google.maps.event.trigger(map, "resize");
		$scope.mapLoaded = true;

		let marker = new google.maps.Marker({
			position: $scope.currentPos,
			map: map,
			title: 'Drag me',
			draggable: true
		});

		marker.addListener('dragend', function(event) {
			$scope.currentPos = {
				lat: event.latLng.lat(),
				lng: event.latLng.lng()
			};
		});

		$scope.$watch("currentPos", function(newValue, oldValue) {
			if (newValue != null) {
				marker.setPosition(newValue);
				map.setCenter(newValue);
				map.setZoom(14);
			}
		});

		$scope.centerInMarker = function() {
			map.panTo(marker.getPosition());
			marker.setMap(null);
		}
	});

	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(pos) {
			$scope.currentPos = {
				lat: pos.coords.latitude,
				lng: pos.coords.longitude
			};
		}, function(error) {
			handleGeolocationError();
		});
	} else {
		handleGeolocationError();
	}

	function handleGeolocationError() {
		$http.post("https://www.googleapis.com/geolocation/v1/geolocate?key=" + Const.google.geolocation).then(function(response) {
			$scope.currentPos = response.data.location;
		});
	}

	$scope.ok = function() {
		$scope.centerInMarker();
		$uibModalInstance.close($scope.currentPos);
	}

	$scope.cancel = function() {
		$uibModalInstance.dismiss();
	}
});