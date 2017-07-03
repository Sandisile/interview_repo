var app = angular.module('app', ['ui.bootstrap']);

app.controller('expandCollapseController', ['$scope', '$uibModal', function($scope) {

  $scope.data = [{
    "state": "VA",
    "value": '14',
    "city": [{
      "cName": 'Virginia Beach',
      "pop": '650,000'
    }, {
      "cName": 'Richmond',
      "pop": '850,000'
    }]
  }, {
    "state": "TX",
    "value": '31',
    "city": [{
      "cName": 'Austin',
      "pop": '990,000'
    }, {
      "cName": 'Houston',
      "pop": '1,450,000'
    }]
  }];

  $scope.expandedState = null;
  $scope.expandedCity = null;

  $scope.manageCollapseExpand = function(obj, isCity) {
    obj.expanded = !obj.expanded;
    if (obj !== $scope.expandedState && obj !== $scope.expandedCity && obj.expanded) {
      $scope.collapseExpanded(isCity);
    }
    if (obj.expanded) {
      if (isCity) {
        $scope.expandedCity = obj;
      } else {
        $scope.expandedState = obj;
      }
    }
  }

  $scope.collapseExpanded = function(clickedOnCity) {
    if (!clickedOnCity && $scope.expandedState !== null) {
      $scope.expandedState.expanded = false;
    }
    if ($scope.expandedCity !== null) {
      $scope.expandedCity.expanded = false;
    }
  };

}]);