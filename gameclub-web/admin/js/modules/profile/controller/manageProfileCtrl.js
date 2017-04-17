angular.module("Profile").controller('AddProfileCtrl', function($scope, navigation, forEach, getIndexOfArray, sweet, rest) {
	$scope.profile = {
		crossNavigation: []
	};

	navigation.$promise.then(function(data) {
		$scope.navigation = data;
	});

	$scope.selectedChanged = function(child) {
		if (child.children.length > 0) {
			forEach(child.children, function(ch) {
				if (child.selected) {
					ch.selected = true;
					addRemoveNavigation(child);
				} else {
					ch.selected = false;
					addRemoveNavigation(child);
				}
				
			});
		}

		let parent = findInTree($scope.navigation, child.parent);

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
		forEach($scope.profile.crossNavigation, function(cross) {
			delete cross.parent;
		});

		console.log("profile: ", $scope.profile);

		sweet.save(function() {
			rest("profile/save").post($scope.profile, function() {
				sweet.success();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
		});
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