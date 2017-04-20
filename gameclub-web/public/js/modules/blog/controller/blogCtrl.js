angular.module("Blog").controller('BlogCtrl', function($scope, idBlog) {

    $scope.prueba = "NORMAL";
    if(idBlog != null) {
        $scope.prueba = "TENGO SELECCIONADO EL BLOG";
    }

});
