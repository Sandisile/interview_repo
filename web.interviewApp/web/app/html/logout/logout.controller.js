/**
 * 
 */

(function () {
    'use strict';
 
    angular.module('Authentication')
        .controller('LogoutController',
            ['$scope', '$rootScope', '$state', 'AuthenticationService','FlashService',
        	function LogoutController( $scope, $rootScope,$state,AuthenticationService, FlashService) {
	            //$scope.username ='metmom\\';
            	
            	//$templateCache.remove('partials/menu-toggle.tmpl.html');
            	$state.go('login');
            	//$window.location.reload();
			        
		      
            }]);
 
})();