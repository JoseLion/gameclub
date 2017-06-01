angular.module("Avatar").controller('ViewAvatarsCtrl', function($scope, avatars, sweet, rest, $uibModal) {

	avatars.$promise.then(function(data) {
		$scope.avatars = data;
	});

	$scope.addAvatar = function() {
		let modal = $uibModal.open({
			size: 'md',
			backdrop: 'static',
			templateUrl: 'js/modules/avatar/view/addAvatar.html',
			controller: addAvatarCtrl
		});
		modal.result.then(function(data) {
			if(data != null) {
				$scope.avatars.push(data);
			}
		});
	};

	$scope.changeStatus = function(avatar) {
		sweet.changeStatus(function() {
			rest("avatar/changeStatus/:id").get({id: avatar.id}, function(data) {
				let index = $scope.avatars.indexOf(avatar);
				$scope.avatars[index].status = data;
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
	};

	let addAvatarCtrl = function($scope, $uibModalInstance, sweet, rest) {
		$scope.cancel = function() {
			$uibModalInstance.dismiss();
		};
		$scope.saveAvatar = function() {
			let formData = {
				avatar: $scope.avatar,
				image: $scope.newAvatar
			};

			sweet.save(function() {
				rest("avatar/save").multipart(formData, function(data) {
					sweet.success();
					sweet.close();
					$uibModalInstance.close(data);
				}, function(error) {
					sweet.error(error.data != null ? error.data.message : error);
				});
			});
		};
	}

});
