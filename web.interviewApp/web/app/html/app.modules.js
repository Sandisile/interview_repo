/**
 * 
 */

(function(){
  'use strict';
  angular.module('common.services', []);
  angular.module('Authentication',[]);
  angular.module('myMenuApp.controllers', ['common.directives']);
  angular.module('common.directives', ['common.services']);
  angular.module('myApp',['ngMaterial', 'ngMessages', 'material.svgAssetsCache', 'ui.bootstrap']);
  angular.module('myMenuApp',['myApp']);
  
  angular.module('MainApp',['angular-growl',  'ngAnimate', 'myMenuApp'])
	.config(['growlProvider', function (growlProvider) {
		  growlProvider.globalTimeToLive(10000);
		  growlProvider.globalDisableCountDown(true);
		  growlProvider.onlyUniqueMessages(false);
}]);
  
 
})();  
  