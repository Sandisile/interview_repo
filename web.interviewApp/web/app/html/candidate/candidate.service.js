/**
 * 
 */

(function () {
'use strict';

	angular.module('myApp').factory('CandidateService', CandidateService);
 
	CandidateService.$inject = ['$http'];
	function CandidateService($http) 
	{
		var service = {};
		service.getAllCandidates = getAllCandidates;
		service.getAllAgencies = getAllAgencies;
		service.getAllQualifications =getAllQualifications;
		service.createCandidate = createCandidate;
		service.deleteCandidate = deleteCandidate;
		service.addComment = addComment;
		service.updateCandidate = updateCandidate;
		service.getById = getById;
		
/*
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;*/

		//service.createApplication = createApplication;
/*service.Update = Update;
        service.Delete = Delete;*/
		return service;
 
		function getAllCandidates() 
		{
			return $http.get('/services.interviewApp/api/v1/getAllCandidates')
			.then(handleSuccess, handleError('Error getting all Candidates, try again later'));
		}

		function deleteCandidate(candidateId)
		{
				return $http.delete('/services.interviewApp/api/v1/deleteCandidate/'+candidateId)
				.then(handleSuccess, handleError('Error deleting candidate, try again later'));
		}

		function getById(candidateId) {

				return $http.get('/services.interviewApp/api/v1/findCandidate/'+candidateId)
					.then(handleSuccess, handleError('Error getting candidate by id'));
				 }
/*
        function GetById(id) {
            return $http.get('/api/users/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }
 http://10.1.94.155:8080/services.interviewApp/api/v1/getAllCandidates
        function GetByUsername(username) {
            return $http.get('/api/users/' + username).then(handleSuccess, handleError('Error getting user by username'));
        }
 */
		function getAllQualifications() 
		{
				return $http.get('/services.interviewApp/api/v1/getAllQualifications')
				.then(handleSuccess, handleError('Error getting all Qualifications, try again later'));
		}

		function getAllAgencies() 
		{
			return $http.get('/services.interviewApp/api/v1/getAllAgencies')
			.then(handleSuccess, handleError('Error getting all Agencies, try again later'));
		}
		
		function createCandidate(candidate)
		{
			return $http.post('/services.interviewApp/api/v1/addCandidate', candidate)
			.then(handleSuccess, handleError('Error in adding Candidate, try again later'));
		}

		function addComment(comment)
		{
			return $http.post('/services.interviewApp/api/v1/addComment', comment)
			.then(handleSuccess, handleError('Error Adding Comment'));
		}
			
		function updateCandidate(candidate) {
			return $http.put('/services.interviewApp/api/v1/updateCandidate', candidate)
			.then(handleSuccess, handleError('Error updating candidate'));
		}

		function serach (candidate) {
			return $http.get()
				.then(handleSuccess, handleError('Error searching candidate'));
		}

/*
        function (applicationList)
		{
             this.applicationList = applicationList;	
        }
 
        function Update(user) {
            return $http.put('/api/users/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }
 
        function Delete(id)
 		{
            return $http.delete('/api/users/' + id).then(handleSuccess, handleError('Error deleting user'));
        }
 */
// private functions

		function handleSuccess(res) {
			//data.success = true ;
//res.data = res.data;
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

