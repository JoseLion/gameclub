angular.module('GameClub').controller('MainCtrl', function($scope, $rootScope, $state, Const, $http, urlRestPath, $cookies, openRest, forEach, getImageBase64) {
	openRest("category/findAll", true).get(function(data) {
		$rootScope.categories = data;

		forEach($rootScope.categories, function(category) {
			openRest("archive/downloadFile").download({name: category.whiteVector.name, module: category.whiteVector.module}, function(data) {
				category.whiteBase64 = getImageBase64(data, category.whiteVector.type);
			});

			openRest("archive/downloadFile").download({name: category.blackVector.name, module: category.blackVector.module}, function(data) {
				category.blackBase64 = getImageBase64(data, category.blackVector.type);
			});
		});
	});

	openRest("console/findAll", true).get(function(data) {
		$rootScope.consoles = data;

		forEach($rootScope.consoles, function(cnsl) {
			openRest("archive/downloadFile").download({name: cnsl.whiteLogo.name, module: cnsl.whiteLogo.module}, function(data) {
				cnsl.whiteBase64 = getImageBase64(data, cnsl.whiteLogo.type);
			});

			openRest("archive/downloadFile").download({name: cnsl.blackLogo.name, module: cnsl.blackLogo.module}, function(data) {
				cnsl.blackBase64 = getImageBase64(data, cnsl.blackLogo.type);
			});
		});
	});

	$rootScope.link = {
		shareAndPlay : {
			name: 'Share & Play',
			route: 'gameclub.home({anchor:"how-it-works"})'
		},
		faq : {
			name: 'Preguntas',
			route: ''
		},
		blog : {
			name: 'Blog',
			route: 'levelapBlog.blog.home'
		},
		login : {
			name: 'Inicia sesión',
			route: 'gameclub.login'
		},
		account : {
			name: '',
			route: 'gameclub.account'
		},
		termsConditions : {
			name: 'Términos y Condiciones',
			route: 'gameclub.termsConditions({user:user, isFacebook:isFacebook})'
		}
	};
});