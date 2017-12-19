angular.module('Search').controller('SearchCtrl', function($scope, $rootScope, games, search, $state, friendlyUrl, getIndexOfArray, openRest, notif, Const, forEach) {
    $scope.search = {};
    $scope.totalPages;

    if (search != null) {
        setSerchFields();
    }

    games.$promise.then(function(data) {
        setPagedData(data);
    });

    $scope.find = function() {
        if($scope.search.console == null) {
            notif.danger(Const.errorMessages.consoleRequired);
        } else {
            $state.go("^.search", {
                name: $scope.search.name != null ? $scope.search.name : "",
                categoryId: $scope.search.category != null ? $scope.search.category.id : null,
                consoleId: $scope.search.console.id,
                page: $scope.search.page,
                title: friendlyUrl(($scope.search.name != null ? ($scope.search.name.trim() + " ") : "") + $scope.search.console.name + ($scope.search.category != null ? (" " + $scope.search.category.name) : "") + " page " + ($scope.search.page != null ? $scope.search.page + 1 : 1))
            });
        }
    }

    $scope.getConsole = function(game) {
        let cnsl = null;

        forEach(game.consoles, function(cross) {
            if (cross.console.id == search.consoleId) {
                cnsl = cross.console;
                return 'break';
            }
        });

        return cnsl;
    }

    $scope.pageChanged = function() {
        $scope.search.page = $scope.currentPage;
        $scope.find();
    }

    function setPagedData(data) {
        $scope.games = data.content;
        $scope.totalPages = data.totalPages;
        $scope.currentPage = data.number;
    }

    function setSerchFields() {
        if ($rootScope.categories != null && $rootScope.consoles != null) {
            $scope.search.name = search.name;

            let index = getIndexOfArray($rootScope.categories, 'id', search.categoryId);
            $scope.search.category = $rootScope.categories[index];

            let index2 = getIndexOfArray($rootScope.consoles, 'id', search.consoleId);
            $scope.search.console = $rootScope.consoles[index2];

            $scope.search.page = search.page;
        } else {
            setTimeout(function() {
                $scope.$apply(function() {
                    setSerchFields();
                });
            }, 500);
        }
    }

    $scope.nameAutocomplete = [];
    $scope.$watch('search.name', function(newValue, oldValue) {
        if(newValue != null && newValue != '' && newValue.length % 3 == 0) {
            openRest("game/findAutocomplete/:name", true).get({name: newValue}, function(data) {
                $scope.nameAutocomplete = data;
            });
        }
    });

});
