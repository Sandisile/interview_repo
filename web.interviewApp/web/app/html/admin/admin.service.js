/**
 * 
 */

(function () {
	'use strict';
	
	angular.module('myApp').factory('AdminService', AdminService);
	
	AdminService.$inject = ['$http'];
	function AdminService($http)
	{
		var adminService = {};
		adminService.getAllUsers = getAllUsers;
		adminService.deleteUser = deleteUser;
		adminService.addUser = addUser;
		adminService.getAllRoles = getAllRoles;
		
		return adminService;
		
		function getAllUsers() {
			return $http.get('/services.interviewApp/api/v1/getAllUsers')
			.then(handleSuccess, handleError('Error in getting all Users,  try again later'));
		}
		
		function deleteUser() {
			return $http.delete('')
					.then(handleSuccess, handleError('Error deleting User, try again later'));
		}
		
		function addUser(user) {
			return $http.post('/services.interviewApp/api/v1/addUser', user)
					.then(handleSuccess, handleError('Error in adding User, try again later'));
		}
		
		function getAllRoles() {
			return $http.get('/services.interviewApp/api/v1/getAllRoles')
			.then(handleSuccess, handleError('Error in getting all Roles,  try again later'));
		}
function handleSuccess(res) {
return { res, success: true };
}
 
		function handleError(error) 
		{
			return function () 
			{
				return { success: false, message: error };
			};
		}
		
	}
})();