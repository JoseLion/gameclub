/*!
 * articleComments.js - v0.1
 * http://www.levelapsoftware.com
 * License: MIT
 * Requirements:
 * - RESTful Web Services
 * - openRest factory
 * - notif factory
 */
angular.module('LevelapBlog').directive('articleComments', function($rootScope, BlogConst, openRest, notif) {
    let baseSrc;
    for (let i = document.getElementsByTagName("script").length - 1; i >= 0; i--) {
        let script = angular.element(document.getElementsByTagName("script")[i]);
        if (script.attr("src") != null && script.attr("src").indexOf("levelapBlog.js") > -1) {
            baseSrc = script.attr("src").substring(0, script.attr("src").indexOf("levelapBlog.js"));
            break;
        }
    }
    return {
        restrict: 'E',
        templateUrl: baseSrc.concat('resources/articleComments.html'),
        required: 'ngModel',
        scope: {
            ngModel: '=',
            level: '=',
            page: '=',
            articleId: '='
        },
        replace: true,
        link: function($scope, element, attrs, ctrl) {
            $scope.$watch('ngModel', function(newValue, oldValue) {
                if(newValue != null) {
                    $scope.result = newValue.content;
                    $scope.showMore = !newValue.last;
                }
            });
            $scope.showMoreComments = function() {
                $scope.page = $scope.page + 1;
                openRest("levelapBlog/getCommentsOf/:articleId/:page").get({articleId: $scope.articleId, page: $scope.page}, function(data) {
                    $scope.result.pushArray(data.content);
                    $scope.showMore = !data.last;
                });
            };
            Array.prototype.pushArray = function(newValues) {
                for(let idx = 0 ; idx < newValues.length ; idx++) {
                    this.push(newValues[idx]);
                }
                return this;
            };
        }
    };
});
