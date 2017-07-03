/**
 * 
 */

(function() {
    'use strict';

    angular.module('myApp')
    .controller('PagerController', PagerController);
    PagerController.$inject = ['PagerService'];
    function PagerController(PagerService) {
    var vm = this;

    vm.dummyItems = [];
    vm.pager = {};
    vm.setPage = setPage;
    vm.dumie = dumie;
    initController();

    function initController() {
        // initialize to page 1
    	 vm.dumie();
        vm.setPage(1);
    }

    function dumie() {
    	vm.dummyItems =	_.range(1, 40); // dummy array of items to be paged
    }
    function setPage(page) {
        if (page < 1 || page > vm.pager.totalPages) {
            return;
        }

        // get pager object from service
        vm.pager = PagerService.GetPager(vm.dummyItems.length, page);

        // get current page of items
        vm.items = vm.dummyItems.slice(vm.pager.startIndex, vm.pager.endIndex + 1);
    }
}
})();