/**
 * 
 */

(function() {
	'use strict';
	
	angular.module('myApp').controller('AddCandidateController',AddCandidateController);
	
	AddCandidateController.$inject =['CandidateService','$state', '$scope', '$rootScope', 'growl','$timeout'];
	
	function AddCandidateController(CandidateService,$state, $scope, $rootScope,growl, $timeout) {
		var inCtrl = this;
		
		inCtrl.getAllAgencies = getAllAgencies;
		inCtrl.getAllQualifications = getAllQualifications;
		inCtrl.addCandidate = addCandidate;
		inCtrl.addQualification = addQualification;
		inCtrl.update = update;

		inCtrl.applicationList = {};
		inCtrl.candidate = {};
		inCtrl.agencies = [];
		inCtrl.application = {};
		
		inCtrl.qualifications = [];
		inCtrl.qualificationList = [];
		inCtrl.qualification = {};
		inCtrl.agency = {};
		inCtrl.regex = '\\d+';
		
	  
		initController();
		function initController()
		{
			getAllAgencies();
			getAllQualifications();
		}

		
		function addCandidate() 
		{
		
			inCtrl.agency.agencyName = inCtrl.agency.agencyName.trim();
			inCtrl.applicationList.agency = inCtrl.agency;
			inCtrl.applicationList.qualificationList = inCtrl.qualificationList;
			inCtrl.candidate.application = inCtrl.applicationList;
			CandidateService.createCandidate(inCtrl.candidate)
				.then(function (response)
				{
					//response = response.data;
					if(response.success && response.res.status === 201)
					{
						growl.success('Candidate Added Successfully');
						$timeout (function () {
							$state.go('home.viewCandidates');
						},5000);
						
					}
					else
					{
						growl.error('Error in adding Candidate, try again later');
					}
				
				},
				function(errResponse) {

					growl.error('Ooops!, Something went wrong try again later');
        		});
		}

		function update() {
			
			$scope.dataLoading = true;
			if(!$rootScope.candidate) {
				
				growl.error('Error in editing Candidate, try again later');
			}
			
			inCtrl.candidate.candidateId = $rootScope.candidate.candidateId;
			inCtrl.candidate.firstname = $rootScope.candidate.firstname;
			inCtrl.candidate.surname = $rootScope.candidate.surname;
			inCtrl.candidate.idNo = $rootScope.candidate.idNo;
			inCtrl.candidate.eeStatus = $rootScope.candidate.eeStatus;
			
			if($rootScope.candidate.applicationList.applicationId) {
				
				inCtrl.application.applicationId = $rootScope.candidate.applicationList.applicationId;
			}
			
			inCtrl.application.applicationDate = $rootScope.candidate.applicationList.dateConversion;
			inCtrl.application.agency = inCtrl.agency;
			inCtrl.application.experience = $rootScope.candidate.applicationList.experience;
			inCtrl.application.currentSalary = $rootScope.candidate.applicationList.currentSalary;
			inCtrl.application.expectedSalary = $rootScope.candidate.applicationList.expectedSalary;
			inCtrl.application.applicationStatus = $rootScope.candidate.applicationList.applicationStatus;
			inCtrl.application.qualification = inCtrl.qualification;
			inCtrl.candidate.application = inCtrl.application;

			CandidateService.updateCandidate(inCtrl.candidate)
				.then(function (response)
					{
						//response = response.data;
						if(response.success && response.res.status === 201)
						{
						
							growl.success('Candidate updated Successfully');
							$timeout(function () {
								$state.go('home.viewCandidates');
							},5000)
							
							
						}
						else
						{

							growl.error('Error in updating Candidate, try again later');
						}
					
					},
					function(errResponse) {

						growl.error('Ooops! Something went wrong try again later');
	        		});
			
		}
		
		function getAllAgencies()
		{
			
	        CandidateService.getAllAgencies()
	            .then( function(response) 
	            {
	            	if(!response.success) {
	            		$state.go('home');
	            		growl.error(response.message);
	            	}
	            	
	            	inCtrl.agencies = response.res.data;
	            	
	            }, function(errResponse) {
	            	//errResponse.message ='Ooops!, Something went wrong try again later';
            		$state.go('home');
            		growl.error('Ooops! Something went wrong try again later');
            		
	            });
		}
		
		function getAllQualifications()
		{
			
	        CandidateService.getAllQualifications()
	            .then( function(response) 
	            {
	            	if(!response.success) {
	            		
	            		//FlashService.Error(response.message, true);
	            		$state.go('home');
	            		growl.error('Ooops! Something went wrong try again later');
	            	}
	            	
            		inCtrl.qualifications = response.res.data;
	            	
	           },  function(errResponse) {
	            	//errResponse.message ='Ooops!, Something went wrong try again later';
            		//FlashService.Error(errResponse.message, true);
            		$state.go('home');
            		growl.error('Ooops! Something went wrong try again later');
	            });
		}
		
		function addQualification()
		{
			inCtrl.qualificationList.push(inCtrl.qualification);
			console.log("Qualifications ADDED:" +inCtrl.qualificationList.qualificationName)
		}

	}
})();