angular.module("LevelapBlogAdmin").controller('ManageCommentsCtrl', function($scope, $rootScope, article, comments, sweet, rest) {
	verifyAuth(0);

	let baseSrc;

	for (let i = document.getElementsByTagName("script").length - 1; i >= 0; i--) {
		let script = angular.element(document.getElementsByTagName("script")[i]);

		if (script.attr("src") != null && script.attr("src").indexOf("levelapBlog.js") > -1) {
			baseSrc = script.attr("src").substring(0, script.attr("src").indexOf("levelapBlog.js"));
			break;
		}
	}

	$scope.userPlaceholder = baseSrc + "resources/user-placeholder.png";

	article.$promise.then(function(data) {
		$scope.article = data;
	});

	comments.$promise.then(function(data) {
		console.log("data: ", data);
		$scope.comments = data.content;
	});

	$scope.isToday = function(millis) {
		let date = new Date(millis);
		let today = new Date();

		if (date.setHours(0, 0, 0, 0) == today.setHours(0, 0, 0, 0)) {
			return true;
		} else {
			return false;
		}
	}

	$scope.addComment = function(obj) {
		obj.isLoading = true;
		let comment = {
			username: $rootScope.currentUser.fullName,
			email: $rootScope.currentUser.username,
			comment: obj.newReply,
			parentId: obj.id,
			articleId: $scope.article.id
		};

		rest("levelapBlog/saveComment").post(comment, function(data) {
			if (obj.body != null) {
				$scope.comments.unshift(data);
			} else {
				if (obj.children == null) {
					obj.children = [];
				}

				obj.children.unshift(data);
				obj.childrenSize++;
			}

			obj.newReply = null;
			obj.isLoading = false;
			obj.isReplying = false;
		}, function(error) {
			obj.isLoading = false;
		});
	}

	function verifyAuth(counter) {
		if (counter == null) {
			counter = 0;
		}

		if ($rootScope.currentUser == null || $rootScope.currentUser.fullName == null || $rootScope.currentUser.username == null) {
			if (counter < 5) {
				setTimeout(function() {
					$scope.$apply(function() {
						counter++;
						verifyAuth(counter);
					});
				}, 1000);
			} else {
				console.error("Modify 'manageCommentsCtrl.js' to set the current logged user");
			}
		}
	}
});