angular.module("LevelapBlogAdmin", []).config(function($stateProvider) {
	let prefix = 'levelapBlog.';
	let baseSrc;

	for (let i = document.getElementsByTagName("script").length - 1; i >= 0; i--) {
		let script = angular.element(document.getElementsByTagName("script")[i]);

		if (script.attr("src").indexOf("levelapBlog.js") > -1) {
			baseSrc = script.attr("src").substring(0, script.attr("src").indexOf("levelapBlog.js"));
			break;
		}
	}

	$stateProvider
	.state(prefix + 'viewArticles', {
		url: "/view-articles",
		templateUrl: baseSrc + "view/viewArticles.html",
		data: {displayName: 'Ver Artículos'},
		controller: "ViewArticlesCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'LevelapBlogAdmin',
					files: [baseSrc + 'controller/viewArticlesCtrl.js']
				}]);
			},

			articles: function(rest) {
				return rest("levelapBlog/findArticles").post(function(data) {
					return data;
				});
			},

			categories: function(rest, BlogConst) {
				if (BlogConst.config.hasCategories) {
					return rest("levelapBlog/getCategories", true).get(function(data) {
						return data;
					});
				}

				return null;
			},

			tags: function(rest, BlogConst) {
				if (BlogConst.config.hasTags) {
					return rest("levelapBlog/getTags", true).get(function(data) {
						return data;
					});
				}
				
				return null;
			}
		}
	})

	.state(prefix + 'addArticle', {
		url: "/add-article",
		templateUrl: baseSrc + "view/manageArticle.html",
		data: {displayName: 'Agregar Artículo'},
		controller: "ManageArticleCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'LevelapBlogAdmin',
					files: [baseSrc + 'controller/manageArticleCtrl.js']
				}]);
			}
		}
	});
}).constant('BlogConst', {
	config: {
		hasCategories: true,
		hasTags: true
	},

	tableSize: 20
}).run(function($rootScope, BlogConst) {
	$rootScope.BlogConst = BlogConst;
});