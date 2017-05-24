/*!
 * articleComments.js - v0.1
 * http://www.levelapsoftware.com
 * License: MIT
 * Requirements:
 * - RESTful Web Services
 * - openRest factory
 * - notif factory
 */
angular.module('LevelapBlog').directive('articleComments', function($rootScope, BlogConst, notif) {
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
            level: '='
        },
        replace: true,
        link: function($scope, element, attrs, ctrl) {
            $scope.result = [];
            $scope.commentsLevel = $rootScope.BlogConst.commentsLevel
            $scope.currentUser = $rootScope.currentUser;
            $scope.showCommentForm = false;
            $scope.$watch('ngModel', function(newValue, oldValue) {
                if(newValue != null) {
                    $scope.result.pushArray(newValue.content);
                    $scope.showMore = !newValue.last;
                }
            });
            /* TODO CARGAR MÁS COMENTARIOS */
            $scope.showMoreComments = function() {
                $scope.result.pushArray(
                    [
                        {
                            username: 'usuario1',
                            comment: 'Comentario 1',
                            level: level
                        }, {
                            username: 'usuario2',
                            comment: 'Comentario 2Comentario 2Comentario 2',
                            level: level
                        }, {
                            username: 'usuario3',
                            comment: 'Comentario 3',
                            level: level
                        }
                    ]
                );
                $scope.showMore = false;
            };
            /* TODO CARGAR COMENTARIOS HIJOS */
            $scope.showChildComments = function() {
                $scope.result.pushArray(
                    [
                        {
                            username: 'usuario1',
                            comment: 'Comentario 1',
                            level: level
                        }, {
                            username: 'usuario2',
                            comment: 'Comentario 2Comentario 2Comentario 2',
                            level: level
                        }, {
                            username: 'usuario3',
                            comment: 'Comentario 3',
                            level: level
                        }
                    ]
                );
                $scope.showMore = false;
            };

            $scope.form = {};
            $scope.submitComment = function($event) {
                let isValid = true;
                if($scope.currentUser != null) {
                    $scope.form.username = $scope.currentUser.fullName;
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
                if($scope.form.email == null) {
                    angular.element($event.target).find('.email-input').addClass('error');
                    angular.element($event.target).find('.email-input input').on('keypress', function(par1) {
                        angular.element($event.target).find('.email-input').removeClass('error');
                    });
                    isValid = false;
                } else {
                    angular.element($event.target).find('.email-input').removeClass('error');
                }
                if(!isValid) {
                    notif.danger(BlogConst.messages.required);
                } else {
                    /* TODO LLAMAR SERVICIO WEB PARA GUARDAR EL COMENTARIO Y ACTUALIZAR LA LISTA DE COMENTARIOS */
                    console.log('FORMULARIO LLENO: ', $scope.form);
                }
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
