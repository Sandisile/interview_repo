/**
 * 
 */

(function () {

  'use strict';

// Declare app level module which depends on filters, and services

  angular.module('myApp', ['Authentication', 'myMenuApp.controllers','ngAnimate','ui.router','ngMaterial','ngAria','ngCookies'])
  	.run(function run($rootScope,$state,$cookieStore, $http,$stateParams) {
  		
  		$rootScope.$state = $state;
  		$rootScope.$stateParams = $stateParams;
  		$rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authData;
        }
        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
            // redirect to login page if not logged in
            if (toState.name !== 'login' && !$rootScope.globals.currentUser) {
            	event.preventDefault();
                $state.go('login');
            }
        });
  		
  		$rootScope.$on('$stateChangeError', function(evt, to, toParams, from, fromParams, error) {
  			if(error.redirectTo) {
  				$state.go(error.redirectTo);
  			}else {
  				$state.go('error', {status: error.status})
  			}
  			
  				
  		})
  		

  		
  	})
	.config(config)
    .filter('nospace',filter)
	.filter('humanizeDoc',filter);
  
  	config.$inject = ['$mdThemingProvider','$qProvider','$stateProvider', '$urlRouterProvider','$compileProvider'];
	function config($mdThemingProvider, $qProvider,$stateProvider, $urlRouterProvider, $compileProvider) {

		$mdThemingProvider.theme('default')
		.primaryPalette('blue', {
		  'default': '300'
		})
		.accentPalette('red', {
		  'default': '500'
		});
		
		
		$compileProvider.preAssignBindingsEnabled(true);
		$qProvider.errorOnUnhandledRejections(false);
		$urlRouterProvider.otherwise('login');
		
		$stateProvider
			.state('login', {
		        url: '/login',
		        controller: 'LoginController as inCtrl',
		        templateUrl: 'login/login.html'
		        
		    })
			.state('home', {
			url: '/home',
			
			views: {
			
			'@': {
			  templateUrl: 'welcome/home.html',
			  controller: 'HomeCtrl as vm'
			}
			}
			})
			.state('home.addcandidate', {
			url: '/addCandidate',
			views: {
			
			'content@home': {
			  templateUrl: 'candidate/addCandidate.html',
			  controller: 'AddCandidateController',
			  controllerAs: 'inCtrl'
			}
			}
			})
			.state('home.viewCandidates', {
			url: '/viewCandidates',
			views: {
			
			'content@home': {
			  templateUrl: 'candidate/candidate.html',
			  controller: 'CandidateController',
			  controllerAs: 'inCtrl'
			}
			}
			})
			.state('home.editCandidate', {
			url: '/edit',
			views: {
			
			'content@home': {
			  templateUrl: 'candidate/editCandidate.html',
			  params : {user: null,},
			  controller: 'AddCandidateController',
			  controllerAs: 'inCtrl'
			}
			}
			})
			.state('home.users', {
			url: '/users',
			views: {
			
			'content@home': {
			  templateUrl: 'admin/userManagement.html',
			  controller: 'UserManagementController',
			  controllerAs: 'adminCtrl'
			}
			}
			})
			.state('home.stats', {
			url: '/stats',
			views: {
			
			'content@home': {
			  templateUrl: 'admin/stats.html',
			  //controller: 'UserManagementController',
			  //controllerAs: 'adminCtrl'
			}
			}
			})
			.state('error', {
			url: '/error',
			
			views: {
			
			'@': {
			  templateUrl: 'error/error.html',
			}
			}
			})
			.state('logout', {
			url: '/login',
			
			views: {
			
			'@': {
			  templateUrl: 'login/login.html',
			  controller: 'LogoutController',
			  controllerAs: 'inCtrl'
			}
			}
			})
			
	}
	
	//take all whitespace out of string
    function filter()
   {
     return function (value) {
       return (!value) ? '' : value.replace(/ /g, '');
     };
   }
   //replace uppercase to regular case
   function filter() {
     return function (doc) {
       if (!doc) return;
       if (doc.type === 'directive') {
         return doc.name.replace(/([A-Z])/g, function ($1) {
           return '-' + $1.toLowerCase();
         });
       }
       
       return doc.label || doc.name;
     };
 }

  })();
