angular.module('Blog').controller('BlogCtrl', function($scope, idBlog) {

    $scope.prueba = 'NORMAL';
    if(idBlog != null) {
        $scope.prueba = 'TENGO SELECCIONADO EL BLOG';
    }

    $scope.blogs = [
        {
            blogId: 1,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/test/blog-preview-01.png',
            date: new Date('11-09-2016'),
            url: '#'
        },
        {
            blogId: 2,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/test/blog-preview-02.png',
            date: new Date('11-09-2016'),
            url: '#'
        },
        {
            blogId: 3,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/test/blog-preview-03.png',
            date: new Date('11-09-2016'),
            url: '#'
        },
        {
            blogId: 4,
            subtitle: 'Artículo',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
            imgSrc: 'img/test/blog-preview-04.png',
            date: new Date('11-09-2016'),
            url: '#'
        }
    ];

    $scope.$watch('currentBlogPage', function(newValue, oldValue) {
        if(newValue != null && newValue != oldValue) {
            console.log('SE DEBE HACER LA CONSULTA PARA LLAMAR A MAS PREVIEWS');
        }
    });

});
