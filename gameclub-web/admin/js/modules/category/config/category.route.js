angular.module("Category").config(function($stateProvider) {
	let prefix = 'portal.'

	$stateProvider
	.state(prefix + 'viewCategories', {
		url: "/view-categories",
		templateUrl: "js/modules/category/view/viewCategories.html",
		data: {displayName: 'Ver Categorias'},
		controller: "ViewCategoriesCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Category',
					files: ['js/modules/category/controller/viewCategoriesCtrl.js']
				}]);
			},

			categories: function(rest) {
				return rest("category/findAll", true).get(function(data) {
					return data;
				});
			}
		}
	})

	.state(prefix + 'addCategory', {
		url: "/add-category",
		templateUrl: "js/modules/category/view/manageCategory.html",
		data: {displayName: 'Agreagr Categoría'},
		controller: "ManageCategoryCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Category',
					files: ['js/modules/category/controller/manageCategoryCtrl.js']
				}]);
			},

			category: function() {
				return null;
			}
		}
	})

	.state(prefix + 'editCategory', {
		url: "/edit-category/:id/:name",
		params: {id: null, name: null},
		templateUrl: "js/modules/category/view/manageCategory.html",
		data: {displayName: 'Editar Categoría'},
		controller: "ManageCategoryCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Category',
					files: ['js/modules/category/controller/manageCategoryCtrl.js']
				}]);
			},

			category: function(rest, $stateParams) {
				return rest("category/findOne/:id").get({id: $stateParams.id}, function(data) {
					return data;
				});
			}
		}
	});
});