
/**
 * 
 *
*/
(function() {
	
	'use strict';

   var app = angular.module('myApp');
   
   /*
 //filter Multiple...
 app.filter('filterMultiple',['$filter',function ($filter) {
 	return function (items, keyObj) {
 		var filterObj = {
 							data:items,
 							filteredData :[],
 							applyFilter : function(obj,key){
 								var fData = [];
 								if(this.filteredData.length == 0)
 									this.filteredData = this.data;
 								if(obj){
 									var fObj = {};
 									if(angular.isString(obj)){
 										fObj[key] = obj;
 										fData = fData.concat($filter('filter')(this.filteredData,fObj));
 									}else if(angular.isArray(obj)){
 										if(obj.length > 0){	
 											for(var i=0;i<obj.length;i++){
 												if(angular.isString(obj[i])){
 													fObj[key] = obj[i];
 													fData = fData.concat($filter('filter')(this.filteredData,fObj));	
 												}
 											}
 											
 										}										
 									}									
 									if(fData.length > 0){
 										this.filteredData = fData;
 									}
 								}
 							}
 				};

 		if(keyObj){
 			angular.forEach(keyObj,function(obj,key){
 				filterObj.applyFilter(obj,key);
 			});			
 		}
 		
 		return filterObj.filteredData;
 	}
 }]);

 myApp.filter('unique', function() {
     return function(input, key) {
         var unique = {};
         var uniqueList = [];
         for(var i = 0; i < input.length; i++){
             if(typeof unique[input[i][key]] == "undefined"){
                 unique[input[i][key]] = "";
                 uniqueList.push(input[i]);
             }
         }
         return uniqueList;
     };
 });
  */
   app.filter("filterByDate", function($filter) {
	      return function(items, from, to) {
	    	  	if(!from && !to) {
	    	  		
	    	  		return items;
	    	  		
	    	  	}
	            return $filter('filter')(items, "applicationDate",function(v){
	              var value = moment(v).format('MM/DD/YYYY');
	              var fromDate = moment(from).format('MM/DD/YYYY');
	              var toDate = moment(to).format('MM/DD/YYYY');
	              
	              var date  = moment(value);
	               
	             return date >= moment(fromDate)&& date <= moment(toDate);
	            });
	      };
	    });
    
   app.controller('CandidateController', CandidateController); //, '$uibModal'
	CandidateController.$inject	=['PagerService', 'CandidateService','$state', '$rootScope','$scope', '$mdDialog', 'growl'] ; 
		function CandidateController(PagerService, CandidateService, $state, $rootScope, $scope, $mdDialog, growl) 
		{
				var inCtrl = this;  
				
				$scope.customFullscreen = false;
				inCtrl.candidates = [];
				$scope.selectCandidate = {};
				$scope.userComment = {};
				$rootScope.candidate = {};
				inCtrl.comment  = {};
				var application = {};
				
				$scope.search = [];
				$scope.filter = {};
				//$scope.endDate = "05/23/2017"	;
				$scope.filterType = '';
				inCtrl.pager = {};
				$scope.expandedCandidate = null;
				$scope.expandedApplication = null;
				
				inCtrl.getAllCandidates = getAllCandidates;
				inCtrl.removeCandidate = removeCandidate;
				inCtrl.applicationBean = applicationBean;
				inCtrl.reset = reset;
				inCtrl.editCandidate = editCandidate;
				inCtrl.setPage = setPage;
				//inCtrl.selectUserComment = selectUserComment;
				
				initController();
				function initController()
				{
					inCtrl.getAllCandidates();
					
				}
				
			    function getAllCandidates()
			    {
			    	$scope.moreInfo = false;
			    	inCtrl.dataLoading = false;
			        CandidateService.getAllCandidates()
			            .then( function(response) 
			            {
			            	
			            	if(!response.success || response.res.data.length === 0) {
			            		
			            		$state.go('home');
			            		growl.error('Ooops! Something went wrong try again later');
			            		
			            	}
			            	inCtrl.dataLoading = true;
		            		inCtrl.candidates = response.res.data;
		            		inCtrl.setPage(1);
			            }, function(errResponse) {
			            	//errResponse.message ='Ooops!, Something went wrong try again later';
		            		//FlashService.Error(errResponse.message, true);
		            		$state.go('home');
		            		growl.error('Ooops! Something went wrong try again later');
			            });
			    }
			    
			    function reset()
			    {
			    	inCtrl.comment = {};
			    }
			    $scope.showSuccess = function() {
			        growl.success('This is a success mesage.', { title: 'Success!' });
			    }

			    /*** Comment Popup ************/
			    

			    $scope.showModal = function(ev) {
				    $mdDialog.show({
				      controller: DialogController,
				      templateUrl: 'dialog1.tmpl.html',
				      parent: angular.element(document.body),
				      targetEvent: ev,
				      clickOutsideToClose:false,
				      fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
				    })

				  };

				  function DialogController($scope, $mdDialog) {
					  $scope.hide = function() {
				      $mdDialog.hide();
				    };

				    $scope.cancel = function() {
				      $mdDialog.cancel();
				    };

				    $scope.submit = function(comment) {
				      $scope.addComment(comment);
				      $mdDialog.hide();
				    };
				    
				    $scope.addComment = function(comment)
					{
						var commentPerson = ($rootScope.globals.currentUser.username).replace('metmom\\','');
						
						if(!commentPerson)
						{
							 $state.go('login');
						}
						inCtrl.comment.commentPerson = commentPerson;
						inCtrl.comment.comment = comment;
						inCtrl.comment.applicationBean = application;
						CandidateService.addComment(inCtrl.comment)
							.then(function (response)
							{
								if(response.success && response.res.status === 201)
								{
									inCtrl.getAllCandidates();
									growl.success('Comment added  Successfully');
								}
								else
								{
									//response.message = 'Error in adding  Comment, try again later';
									inCtrl.reset();
									growl.error('Error in adding  Comment, try again later');
									//FlashService.Error(response.message, true);
									// $state.go('home');
								}
									
							},
							 function(errResponse) {
				            	growl.error('Ooops! Something went wrong try again later');
			        		});
					}
				    
				  }
				  
				  
				 /****** End add Comment Popup *************/
			    
				  /****Show Comments Popup ***********/
				  

				    $scope.showCommentModal = function(ev, userComment) {
				    	 var useFullScreen = $scope.customFullscreen;
					      $mdDialog.show({
					      controller: DialogCommentController,
					      templateUrl: 'showComment.tmpl.html',
					      parent: angular.element(document.body),
					      targetEvent: ev,
					      clickOutsideToClose:true,
					      //scope : $scope,
					      fullscreen: useFullScreen, // Only for -xs, -sm breakpoints.
					      locals: {dataToPass: userComment}
					    });

					  };

					  
					  function DialogCommentController($scope, $mdDialog, dataToPass) {
						  
						  $scope.hide = function() {
					      $mdDialog.hide();
					    };
					    
					    $scope.userComment = dataToPass;
					    
					    $scope.cancel = function() {
				    		 
				    		 $mdDialog.cancel();
					    };
					    
					  }
					  
				/***********End Show Comments popup *********************/
	  
			    function removeCandidate(candidate)
			    {
			    	CandidateService.deleteCandidate(candidate)
			    	.then(function (response) {
			    		if(response.success && response.res.status === 202) {
			    			
			    			inCtrl.getAllCandidates();
			    			growl.success('Candidate Deleted Successfully');
			    		}
			    		else {
			    			growl.error('Error in deleting Candidate, try again later');
			    		}
			    		
			    	},function(errResponse) {
		            	growl.error('Ooops! Something went wrong try again later');
	        		});
			    }
			 
			    
			    $scope.application = {selected: 'No Application Selected'};
			    $scope.candidate = {selected: 'No Candidate Selected'};
			    $scope.triggerChange = function(object) {
			    	
			    		//$scope.moreInfo = !$scope.moreInfo;
			    		var applicationList = object.applicationList;
			    		$scope.selectCandidate.candidateId = object.candidateId;
			    		$scope.selectCandidate.firstname = object.firstname;
			    		$scope.selectCandidate.surname = object.surname;
			    		$scope.selectCandidate.idNo = object.idNo;
			    		$scope.selectCandidate.eeStatus = object.eeStatus;
			    			
			    		var appMax = Math.max(...Array.from(applicationList, o => o.applicationId));
			    		$rootScope.maxAppObject = applicationList.find(o => o.applicationId === appMax);
			    }
			   
				function editCandidate(candidateId) {
					
					CandidateService.getById(candidateId)
						.then(function(response) {
							if(response.success && response.res.status === 200) {
								$rootScope.candidate = response.res.data;
								var appMax = Math.max(...Array.from($rootScope.candidate.applicationList, o => o.applicationId));
					    		var maxAppObject = $rootScope.candidate.applicationList.find(o => o.applicationId === appMax);
					    		$rootScope.candidate.applicationList = maxAppObject;
								$state.go('home.editCandidate');
							}
							else {
								growl.error('Error in editing Candidate, try again later');
							}
						});
				}
				
				function searchCandidate(candidate) {
					
					CandidateService.serach(candidate)
						.then(function(response) {
							if(response.success && response.res.status === 200) {
								$scope.search = response.res.data;
							}
							else {
								
								response.message = 'Error in searching Candidate, try again later';
								response.success = false;
								//FlashService.Error(response.message, true);
								$state.go('home');	
							}
								
						}, function(errResponse) {
							errResponse.message ='Ooops! Something went wrong try again later';
		            		//FlashService.Error(errResponse.message, true);
		            		$state.go('home');
							
						});
				}
			    
			    function applicationBean(objects, event) {
			    	
			    	$scope.showModal(event);
			    	var appMax = Math.max(...Array.from(objects, o => o.applicationId));
		    		application = objects.find(o => o.applicationId === appMax);
			    }
			    
			    
			    /**************************Pagination ***********************************/

		        function setPage(page) {
		            if (page < 1 || page > inCtrl.pager.totalPages) {
		                return;
		            }

		            // get pager object from service
		            inCtrl.pager = PagerService.GetPager(inCtrl.candidates.length, page);

		            // get current page of items
		            inCtrl.candidateList = inCtrl.candidates.slice(inCtrl.pager.startIndex, inCtrl.pager.endIndex + 1);
		        }
		        
		        $scope.manageCollapseExpand = function(obj, isApplication) {
		        	
		        	obj.expanded = !obj.expanded;
		        	if(obj !== $scope.expandedCandidate && obj !== $scope.expandedApplication && obj.expanded) {
		        		
		        		$scope.collapsedExpanded(isApplication);
		        	}
		        	
		        	if(obj.expanded) {
		        		
		        		if(isApplication) {
		        			
		        			$scope.expandedApplication = obj;
		        		}
		        		else {
		        			$scope.expandedCandidate = obj;
		        		}
		        	}
		        }
		        
		     $scope.collapsedExpanded = function(clickedOnApplication) {
		    	 
		    	 if(!clickedOnApplication && $scope.expandedCandidate !== null) {
		    		 
		    		 $scope.expandedCandidate.expanded = false;
		    	 }
		    	 
		    	 if($scope.expandedApplication !== null) {
		    		 
		    		 $scope.expandedApplication.expanded = false;
		    	 }
		     } 
			    
		}
})();
