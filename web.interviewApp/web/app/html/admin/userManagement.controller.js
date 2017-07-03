/**
 * 
 */

( function() {
	
	'use strict';
	
	angular.module('myApp')
		.controller('UserManagementController', UserManagementController);
	UserManagementController.$inject = ['AdminService','$state', '$mdDialog', '$scope', '$rootScope','FlashService'];
	function UserManagementController(AdminService, $state, $mdDialog, $scope, $rootScope, FlashService)
	{
		var adminCtrl = this;
		$scope.customFullscreen = false;
		$scope.roles = [];
		adminCtrl.users = [];
		adminCtrl.user  = {};
		adminCtrl.role = {};
		
		//adminCtrl.addUser = addUser;
		adminCtrl.getAllUsers = getAllUsers;
		adminCtrl.getAllRoles = getAllRoles;
		adminCtrl.showModal = showModal;
		
		initController();
		
		function initController()
		{
			getAllUsers();
			getAllRoles();
			
		}
		
		function getAllUsers() {
			AdminService.getAllUsers()
				.then(function(response) {
					if(!response.success) {
						FlashService.Error(response.message);
						$state.go('home');
					}
					adminCtrl.users = response.res.data;
					
				}, function (errResponse) {
					errResponse.message = 'Ooops!, Something went wrong try again later';
            		FlashService.Error(errResponse.message);
            		$state.go('home');
				});
		}
		
	   function showModal(ev) {
		    $mdDialog.show({
		    	 
		      controller: DialogController,
		      bindToController: true,
		      templateUrl: 'addUser.tmpl.html',
		      parent: angular.element(document.body),
		      targetEvent: ev,
		      clickOutsideToClose:false,
		      scope: $scope,
		      fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
		      roles : $scope.roles
		    })
		   
		  };
		
		  function DialogController($scope, $mdDialog) {
			  $scope.hide = function() {
		      $mdDialog.hide();
		    };
		    
		    $scope.cancel = function() {
		      $mdDialog.cancel();
		    };
		    $scope.submit = function(username, role) {
			      $scope.addUser(username, role);
			      $mdDialog.hide();
			    };
			    
			$scope.addUser = function(username, role) {
				
				adminCtrl.user.username = username;
				adminCtrl.role = role;
				adminCtrl.user.role = adminCtrl.role;
				
				AdminService.addUser(adminCtrl.user)
				.then(function(response) {

					if(response.success && response.res.data && response.res.status === 201)
					{
						getAllUsers();
						$state.reload();
						$state.go('home.users');
					}
					else
					{
						response.message = 'Error in adding  Comment, try again later';
						response.success = false;
						FlashService.Error(response.message);
						 $state.go('home');
					}
						
				},
				 function(errResponse) {
	            	errResponse.message ='Ooops!, Something went wrong try again later';
            		FlashService.Error(errResponse.message);
            		 $state.go('home');
				})
			}
		}
		  
		function getAllRoles() {
		  		
		  		AdminService.getAllRoles()
		  			.then( function(response) {
		  				
		  				if(!response.success) {
		  					FlashService.Error(response.message);
		  					$state.go('home');
		  				}
		  				$scope.roles = response.res.data;
		  			}, function (errResponse) {
		  				errResponse.message ='Ooops!, Something went wrong try again later';
	            		FlashService.Error(errResponse.message);
	            		$state.go('home');
		  			});
		  	}
		  
			    
	}
	
})();
