/**
 * 
 */
(function() {
	
	'use strict';
	var app = angular.module('plunker', []);

	app.controller('MainCtrl', function($scope) {
	  //$scope.startDate = "2016-08-01";
	 // $scope.endDate = "2016-08-03";
	  $scope.records = [
	   
	            {"record" :{ "name" : "2017-05-23", "marks" : 100 }},
	            {"record" :{ "name" : "2017-03-14", "marks" : 100 }},
	            {"record" :{ "name" : "2017-04-24", "marks" : 100 }},
	            {"record" :{ "name" : "2016-07-14", "marks" : 100}}
				           
	        ];
	});
	
	app.filter("filterByDate", function($filter) {
	      return function(items, from, to) {
	    	  
	    	  if(!from && !to) {
	    	  		
	    	  		return items;
	    	  		
	    	  	}
	            return $filter('filter')(items, "names", function(v){
	              var date  = moment(v);
	              if(date >= moment(from) && date <= moment(to)) {
	            	  return date;
	              }
	              
	            });
	      };
	    });

})();