/**
 * 
 */

(function () {
    'use strict';
 
    angular.module('Authentication')
        .controller('LoginController',
            ['$scope', '$rootScope', '$state', 'AuthenticationService','FlashService',
        	function LoginController($scope, $rootScope,$state, AuthenticationService, FlashService) {
	            // reset login status
	            AuthenticationService.ClearCredentials();
	            $scope.username ='metmom\\';
		        $scope.login = function() {
		        	$scope.dataLoading = true;
		        	AuthenticationService.Login($scope.username, $scope.password, function(response) {
		        		if(response !== "" && response.validUser) {
		        			AuthenticationService.SetCredentials(response.username, response.roles);
		        			$state.go('home');
		        			
		        		}
		        		else {
		        				response.message = 'Username or password is incorrect';
			        			FlashService.Error(response.message);
			        			$scope.dataLoading = false;
		        		}
		        	})
		        	
		   
		        };
		      
            }]);
 
})();