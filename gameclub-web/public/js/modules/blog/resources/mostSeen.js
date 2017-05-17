angular.module('Blog').directive('mostSeen', function() {
    return {
        restrict: 'E',
        templateUrl: 'js/modules/blog/resources/mostSeen.html',
        required: 'ngModel',
        scope: {
            ngModel: '=',
            pages: '=',
            activePage: '='
        },
        replace: true,
        link: function($scope, element, attrs, ctrl) {

            $scope.completeBlog = function() {
                console.log('MANDA AL BLOG COMPLETO')
            };


            /**
            * Page section:
            */
            if ($scope.activePage == null) { $scope.activePage = 0; }
            $scope.changePage = function(page) {
                $scope.pageList[$scope.activePage].active = false;
                page.active = true;
                $scope.activePage = page.number;
            }
            $scope.$watch("pages", function(newValue, oldValue) {
                if (newValue != null) {
                    $scope.pageList = [];
                    for(let i = 0; i < newValue; i++) {
                        $scope.pageList.push({
                            number: i,
                            active: ($scope.activePage == i ? true : false)
                        });
                    }
                }
            });

        }
    };
});
