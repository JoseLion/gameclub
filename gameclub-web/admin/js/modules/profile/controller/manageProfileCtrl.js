angular.module("Profile").controller('ManageProfile', function($scope, navigation, profile, forEach, getIndexOfArray, sweet, rest, $state) {
	$scope.profile = {
		crossNavigation: []
	};

	navigation.$promise.then(function(data) {
		$scope.navigation = data;

		if (profile != null) {
			profile.$promise.then(function(profileData) {
				$scope.profile = profileData;

				forEach($scope.profile.crossNavigation, function(cross) {
					let nav = findInTree($scope.navigation, cross.navigation.id);
					nav.selected = true;
				});
			});
		}
	});

	$scope.selectedChanged = function(child) {
		if (child.children.length > 0) {
			forEach(child.children, function(ch) {
				ch.selected = child.selected;
				addRemoveNavigation(ch);
			});
		}

		let parent = findInTree($scope.navigation, child.parentId);

		if (parent != null) {
			let oneSelected = false;
			forEach(parent.children, function(ch) {
				if (ch.selected) {
					oneSelected = true;
					return 'break';
				}
			});

			if (!oneSelected) {
				parent.selected = false;
				addRemoveNavigation(parent);
			} else {
				parent.selected = true;
				addRemoveNavigation(parent);
			}
		}

		addRemoveNavigation(child);
	}

	$scope.save = function() {
		sweet.save(function() {
			rest("profile/save").post($scope.profile, function() {
				sweet.success();
				sweet.close();
				$scope.cancel();
			}, function(error) {
				sweet.error(error.data.message);
			});
		});
	}

	$scope.cancel = function() {
		$state.go("^.viewProfiles");
	}

	function addRemoveNavigation(child) {
		if (child.selected) {
			$scope.profile.crossNavigation.push({
				navigation: child
			});
		} else {
			let index = getIndexOfArray($scope.profile.crossNavigation, 'navigation.id', child.id);
			$scope.profile.crossNavigation.splice(index, 1);
		}
	}

	function findInTree(array, id) {
		let found;

		forEach(array, function(node) {
			if (node.id == id) {
				found = node;
				return 'break';
			}

			if (node.children.length > 0) {
				found = findInTree(node.children, id);

				if (found != null) {
					return 'break';
				}
			}
		});

		return found;
	}
});
