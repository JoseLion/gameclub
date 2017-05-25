/*!
 * articleCommentsForm.js - v0.1
 * http://www.levelapsoftware.com
 * License: MIT
 * Requirements:
 * - RESTful Web Services
 * - openRest factory
 * - notif factory
 */
angular.module('LevelapBlog').directive('articleCommentsForm', function($rootScope, BlogConst, openRest, notif) {
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
        templateUrl: baseSrc.concat('resources/articleCommentsForm.html'),
        required: 'ngModel',
        scope: {
            ngModel: '='
        },
        replace: true,
        link: function($scope, element, attrs, ctrl) {
            $scope.currentUser = $rootScope.currentUser;
            $scope.form = {};
            $scope.submitComment = function($event) {
                let isValid = true;
                let emailValid = true;
                if($scope.currentUser != null) {
                    $scope.form.username = $scope.currentUser.name + " " + $scope.currentUser.lastName;
                    $scope.form.email = $scope.currentUser.username;
                }
                if($scope.form.comment == null) {
                    angular.element($event.target).find('.comment-input').addClass('error');
                    angular.element($event.target).find('.comment-input textarea').on('keypress', function(par1) {
                        angular.element($event.target).find('.comment-input').removeClass('error');
                    });
                    isValid = false;
                } else {
                    angular.element($event.target).find('.comment-input').removeClass('error');
                }
                if($scope.form.username == null) {
                    angular.element($event.target).find('.name-input').addClass('error');
                    angular.element($event.target).find('.name-input input').on('keypress', function(par1) {
                        angular.element($event.target).find('.name-input').removeClass('error');
                    });
                    isValid = false;
                } else {
                    angular.element($event.target).find('.name-input').removeClass('error');
                }
                let emailValidation = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                if($scope.form.email == null || !emailValidation.test($scope.form.email)) {
                    angular.element($event.target).find('.email-input input').on('keypress', function(par1) {
                        angular.element($event.target).find('.email-input').removeClass('error');
                    });
                    angular.element($event.target).find('.email-input').addClass('error');
                    if($scope.form.email == null) {
                        isValid = false;
                    } else {
                        emailValid = false;
                    }
                } else {
                    angular.element($event.target).find('.email-input').removeClass('error');
                }

                if(isValid && emailValid) {
                    $scope.form.articleId = $scope.ngModel;
                    openRest("levelapBlog/saveComment").post($scope.form, function(data) {
                        $scope.form = {};
                        $rootScope.$broadcast('check-comments');
                    });
                } else {
                    if(!isValid) {
                        notif.danger(BlogConst.messages.required);
                    } else {
                        notif.danger(BlogConst.messages.emailValidation);
                    }
                }
            };
        }
    };
});
