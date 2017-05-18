angular.module('LevelapBlog').directive('articleComments', function() {

    return {
        restrict: 'E',
        template: `
            <div id="article-comment">
                <div class="comment" ng-repeat="comment in result">
                    <span>{{comment.username}}</span>
                    <p>{{comment.comment}}</p>
                    <div class="comments-links">
                        <a href ng-click="comment.showReplies = true" ng-if="!comment.showReplies">Mostrar respuestas</a>
                    </div>
                    <article-comments ng-model="comment.resultChildren" ng-if="comment.showReplies"/>
                </div>
                <div class="comments-links">
                    <a href ng-click="showMoreComments()" ng-if="showMore">Ver m&aacute;s</a>
                </div>
            </div>
        `,
        required: 'ngModel',
        scope: {
            ngModel: '='
        },
        replace: true,
        link: function($scope, element, attrs, ctrl) {

            $scope.result = [];

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
                            comment: 'Comentario 1'
                        }, {
                            username: 'usuario2',
                            comment: 'Comentario 2Comentario 2Comentario 2'
                        }, {
                            username: 'usuario3',
                            comment: 'Comentario 3'
                        }
                    ]
                );
                $scope.showMore = false;
            };

            Array.prototype.pushArray = function(newValues) {
                for(let idx = 0 ; idx < newValues.length ; idx++) {
                    /* TODO LLAMAR AL SERVICIO WEB PARA Q TRAIGA LOS COMENTARIOS HIJOS */
                    if(newValues[idx].blogArticle != null && newValues[idx].blogArticle.id == 2) {
                        newValues[idx].resultChildren = {
                            content: [
                                {
                                    username: 'usuario1',
                                    comment: 'Comentario 1'
                                }, {
                                    username: 'usuario2',
                                    comment: 'Comentario 2'
                                }, {
                                    username: 'usuario3',
                                    comment: 'Comentario 3'
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
