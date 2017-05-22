angular.module('LevelapBlog').directive('articleComments', function($rootScope) {
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
            $scope.showMoreComments = function(level) {
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
            $scope.submitComment = function() {
                if($scope.currentUser != null) {
                    $scope.form.username = $scope.currentUser.fullName;
                    $scope.form.email = $scope.currentUser.username;
                }
                console.log('FORMULARIO LLENO: ', $scope.form);
            };

            Array.prototype.pushArray = function(newValues) {
                for(let idx = 0 ; idx < newValues.length ; idx++) {
                    /* TODO LLAMAR AL SERVICIO WEB PARA Q TRAIGA LOS COMENTARIOS HIJOS */
                    if(newValues[idx].blogArticle != null && newValues[idx].blogArticle.id == 2) {
                        newValues[idx].resultChildren = {
                            content: [
                                {
                                    username: 'usuario1',
                                    comment: 'Comentario 1',
                                    level: 1
                                }, {
                                    username: 'usuario2',
                                    comment: 'Comentario 2',
                                    level: 1
                                }, {
                                    username: 'usuario3',
                                    comment: 'Comentario 3',
                                    level: 1
                                }
                            ],
                            last: false
                        }
                        newValues[idx].showReply = false;
                    }
                    this.push(newValues[idx]);
                }
                return this;
            };
        }
    };
});
