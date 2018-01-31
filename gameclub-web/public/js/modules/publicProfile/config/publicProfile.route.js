angular.module('PublicProfile').config(function($stateProvider) {

	let prefix = 'gameclub.';

	$stateProvider

	.state(prefix + 'publicProfile', {
		url: '/publicProfile/:id/:alias',
		params: {id: null, alias: null},	
		templateUrl: 'js/modules/publicProfile/view/publicProfile.html',
		data: {displayName: 'GameClub'},
		metaTags: {
			title: function(seo) {
				return seo.name + " " + seo.lastName.substring(0, 1).toUpperCase() + " GameClub";
			},

			description: 'GameClub ¡La única plataforma gamer del Ecuador! Alquila tus Juegos, Gana Dinero, Juega más pagando menos. Share and Play',

			keywords: 'Alquila, Videojuegos, PS4, Xbox, Nintendo, Juegos Nuevos, Juegos, PC,Consola, Gamer',

			prerender: {
                statusCode: function(seo) {
                    return seo != null ? 200 : 302;
                },

                header: function(seo, friendlyUrl, $location) {
                    return seo != null ? null : 'Location: ' + $location.$$protocol + "://" + $location.$$host + "/gameclub/publicProfile/" + seo.id + "/" + friendlyUrl(seo.name + ' ' + seo.lastName.substring(0, 1));
                }
            }
		},
		controller: 'PublicProfileCtrl',
		resolve: {
			loadPlugin: function($ocLazyLoad) {
				return $ocLazyLoad.load([{
					name: 'PublicProfile',
					files: ['js/modules/publicProfile/controller/publicProfileCtrl.js', 'js/modules/publicProfile/style/publicProfile.less', 'js/modules/publicProfile/style/publicProfile.responsive.less']
				}]);
			},

			user: function(openRest, $stateParams) {
				return openRest("publicUser/findOne/:id").get({id: $stateParams.id});
			},

			reviews: function(rest, $stateParams) {
				return rest("review/getReviewsOfUser/:id/:page").get({id: $stateParams.id, page: 0});
			},

			seo: function(openRest, $stateParams) {
				return openRest("publicUser/findOne/:id").get({id: $stateParams.id}).$promise;
			}
		}
	});
});
