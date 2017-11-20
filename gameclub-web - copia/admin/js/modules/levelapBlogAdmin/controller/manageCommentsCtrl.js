angular.module("LevelapBlogAdmin").controller('ManageCommentsCtrl', function($scope, $rootScope, article, comments, sweet, rest) {
	verifyAuth(0);

	let baseSrc;
	let currentPage;

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
		setPagedComments(data);
	});

	$scope.replyComment = function(reply, $index) {
		reply.isReplying = true;

		setTimeout(function() {
			$scope.$apply(function() {
				angular.element("#textbox_" + $index).focus();
			});
		}, 0);
	}

	$scope.loadMoreComments = function() {
		currentPage++;
		$scope.isLoadingComments = true;

		rest("levelapBlog/getCommentsOf/:articleId/:page").get({articleId: $scope.article.id, page: currentPage}, function(data) {
			setPagedComments(data);
			$scope.isLoadingComments = false;
		});
	}

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
				$scope.comments.push(data);
			} else {
				if (obj.children == null) {
					obj.children = [];
				}

				obj.children.push(data);
				obj.childrenSize++;
			}

			obj.newReply = null;
			obj.isLoading = false;
			obj.isReplying = false;
		}, function(error) {
			obj.isLoading = false;
		});
	}

	$scope.getRepliesOf = function(comment) {
		comment.isLoadingReplies = true;

		rest("levelapBlog/getRepliesOf/:parentId/:page").get({parentId: comment.id, page: 0}, function(data) {
			setPagedReplies(data, comment);
			comment.isLoadingReplies = false;

			for (let i = comment.children.length - 1; i >= 0; i--) {
				comment.children[i].parent.hasMore = !data.last;
				comment.children[i].parent.isLoadingReplies = false;
			}

			comment.repliesLoaded = true;
		});
	}

	$scope.loadMoreReplies = function(comment) {
		let parent = getParentRecursively($scope.comments, comment.parent.id);
		comment.parent.isLoadingReplies = true;

		if (parent.currentPage == null) {
			parent.currentPage = 0;
		}

		parent.currentPage++;

		rest("levelapBlog/getRepliesOf/:parentId/:page").get({parentId: parent.id, page: parent.currentPage}, function(data) {
			setPagedReplies(data, parent);

			for (let i = parent.children.length - 1; i >= 0; i--) {
				parent.children[i].parent.hasMore = !data.last;
				parent.children[i].parent.isLoadingReplies = false;
			}
		});
	}

	$scope.changeStatus = function(comment) {
		sweet.changeStatus(function() {
			rest("levelapBlog/changeCommentStatus/:id/:recursively").get({id: comment.id, recursively: true}, function(data) {
				//comment.status = data; /*IF RECURSIVLY IS FALSE*/
				changeStatusRecursively(comment, data); /*IF RECURSIVLY IS TRUE*/
				sweet.statusChanged();
				sweet.close();
			}, function(error) {
				sweet.close();
			});
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

	function setPagedComments(data) {
		if ($scope.comments == null) {
			$scope.comments = [];
		}

		$scope.comments = $scope.comments.concat(data.content);
		$scope.hasMore = !data.last;
		currentPage = data.number;
	}

	function setPagedReplies(data, comment) {
		if (comment.children == null) {
			comment.children = [];
		}

		comment.children = comment.children.concat(data.content);
		comment.hasMore = !data.last;
		comment.currentPage = data.number;
	}

	function getParentRecursively(array, id) {
		let parent;

		for (let i = array.length - 1; i >= 0; i--) {
			if (array[i].id == id) {
				parent = array[i];
				break;
			}

			if (array[i].children != null) {
				parent = getParentRecursively(array[i].children, id);

				if (parent != null) {
					break;
				}
			}
		}

		return parent;
	}

	function changeStatusRecursively(comment, data) {
		comment.status = data;

		if (comment.children != null) {
			for (let i = comment.children.length - 1; i >= 0; i--) {
				changeStatusRecursively(comment.children[i], data);
			}
		}
	}
});