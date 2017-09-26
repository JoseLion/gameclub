angular.module("Settings").config(function($stateProvider){
	let prefix = 'admin.'

	$stateProvider
	.state(prefix + 'viewSettings',{
		url: "/view-settings",
		templateUrl: "js/modules/settings/view/viewSettings.html",
		data: {displayName: 'Parámetros'},
		controller: 'ViewSettingsCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad){
				return $ocLazyLoad.load([{
					name: 'Settings',
					files: ['js/modules/settings/controller/viewSettingsCtrl.js']
				}]);
			},
			settingsList: function(rest){
				return rest("settings/findAll", true).get(function(data){
					return data;
				});
			}
			/*settingsCategoriesList: function(rest){
				return rest("settings/listCategories", true).get(function(data){
					return data;
				});
			}*/
		}

	})
	.state(prefix + 'addSetting', {
		url: "/add-setting",
		templateUrl: "js/modules/settings/view/manageSetting.html",
		data: {displayName: 'Agregar Parámetro'},
		controller: "ManageSettingCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Settings',
					files: ['js/modules/settings/controller/manageSettingsCtrl.js']
				}]);
			},
			setCat: function(rest){
				return rest("settings/listCategories", true).get(function(data){
					return data;
				});
			},

			settingObj: function() {
				return null;
			}
		}

	})
	.state(prefix + 'editSetting',{
		url: "/edit-setting/:id/:fullName",
		params: {fullName: null, id: null},
		templateUrl: "js/modules/settings/view/manageSetting.html",
		data: {displayName: 'Actualizar Parámetro'},
		controller: "ManageSettingCtrl",
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'Settings',
					files: ['js/modules/settings/controller/manageSettingsCtrl.js']
				}]);
			},
			setCat: function(rest){
				return rest("settings/listCategories", true).get(function(data){
					return data;
				});
			},
			settingObj: function(rest, $stateParams) {
				return rest("settings/findOne/:id").get({id: $stateParams.id}, function(data){
					return data;
				});
			}
		}
	});
});