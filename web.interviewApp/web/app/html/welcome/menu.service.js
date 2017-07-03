/**
 * 
 */

(function(){

'use strict';

  angular.module('common.services',['ngCookies'])
  .factory('menu', ['$location','$cookies','$rootScope','Base64',
      function ($location,$rootScope,$cookies,Base64) {

	  	$rootScope.global = $cookies['globals'] || {};
	  	var roles = Base64.decode($rootScope.global.currentUser.authData);
	  	roles = roles.replace(':','');
	  	
	  	roles = roles.split(",");
	  	
    	var isAdmin = $.inArray('admin',roles) !== -1;
    	var isHR = $.inArray('HR',roles) !== -1;
    	
    	$rootScope.username = ($rootScope.global.currentUser.username).replace('metmom\\','');
        var sections = [{
          name: 'Home',
          state: 'home',
          type: 'link',
          icon: 'fa fa-home'
        }];

        sections.push({
            name: 'User Options',
            type: 'toggle',
            pages: [{
              name: $rootScope.username ,
              type: 'link',
              icon: 'fa fa-user-circle'
            }, {
              name: 'Log Out',
              state: 'logout',
              type: 'link',
              icon: 'fa fa-sign-out'
            }]
          });
        
      if (isAdmin || isHR) {
      
	        sections.push({
	          name: 'Candidates',
	          type: 'toggle',
	          pages: [{
	            name: 'Add Candidate',
	            type: 'link',
	            state: 'home.addcandidate',
	            icon: 'fa fa-user'
	          }, {
	            name: 'View Candidates',
	            state: 'home.viewCandidates',
	            type: 'link',
	            icon: 'fa fa-group'
	          }]
	        });
      	}
      
      if(isAdmin) {
	        sections.push({
	          name: 'Admin',
	          type: 'toggle',
	          pages: [{
	            name: 'Statistics',
	            type: 'link',
	            state: 'home.stats',
	            icon: 'fa fa-line-chart'
	          }
	          /*, {
	            name: 'Add Qualification',
	            state: 'munchies.bananachips',
	            type: 'link',
	            icon: 'fa fa-graduation-cap'
	          },
	            {
	              name: 'Add Agency',
	              state: 'munchies.donuts',
	              type: 'link',
	              icon: 'fa fa-industry'
	            },*/
	           ,{
	            name: 'User Management',
	            state: 'home.users',
	            type: 'link',
	            icon: 'fa fa-users'
	          }
	          ]
	        });
      }

        var self;

        return self = {
          sections: sections,

          toggleSelectSection: function (section) {
            self.openedSection = (self.openedSection === section ? null : section);
          },
          isSectionSelected: function (section) {
            return self.openedSection === section;
          },

          selectPage: function (section, page) {
            page && page.url && $location.path(page.url);
            self.currentSection = section;
            self.currentPage = page;
          }
        };

        function sortByHumanName(a, b) {
          return (a.humanName < b.humanName) ? -1 :
            (a.humanName > b.humanName) ? 1 : 0;
        }

      }])
      
})();