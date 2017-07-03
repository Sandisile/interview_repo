/**
 * 
 */

(function(){
  'use strict';
  
  angular.module('myMenuApp.controllers')

.controller('HomeCtrl', [
	  '$window',
      '$scope',
      '$log',
      '$state',
      '$timeout',
      '$location',
      'menu',
      function ($window, $scope, $log, $state, $timeout, $location, menu) {

        var vm = this;
 
        //functions for menu-link and menu-toggle
        vm.isOpen = isOpen;
        vm.toggleOpen = toggleOpen;
        vm.autoFocusContent = false;
        vm.menu = menu;

        vm.status = {
          isFirstOpen: true,
          isFirstDisabled: false
        };
        
        $scope.reloadPage = function(){$window.location.reload(true);}

        function isOpen(section) {
        	
          return menu.isSectionSelected(section);
        }

        function toggleOpen(section) {
          menu.toggleSelectSection(section);
        }

      }])
})();