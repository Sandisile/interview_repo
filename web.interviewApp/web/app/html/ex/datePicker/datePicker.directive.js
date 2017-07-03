/**
 * 
 */

(function() {
	'use strict';
	
	angular.module('app').directive('myDatepicker', function ($parse) {
	    return function (rootScope, element, attrs, controller) {
	        var ngModel = $parse(attrs.ngModel);
	        $(function(){
	            element.datepicker({
	                showOn:"both",
	                changeYear:true,
	                changeMonth:true,
	                dateFormat:'yy-mm-dd',
	                maxDate: new Date(),
	                yearRange: '2017:2025',
	                onSelect:function (dateText, inst) {
	                	rootScope.$apply(function(rootScope){
	                        // Change binded variable
	                        ngModel.assign(scope, dateText);
	                    });
	                }
	            });
	        });
	    }
	});
})();