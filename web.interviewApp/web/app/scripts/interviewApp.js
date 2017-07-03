/**
 * 
 */

var selected = element(by.binding('candidate.selected'));


it('should initialize to model', function() {
  expect(favorite.getText()).toContain('unicorns');
});
it('should bind the values to the inputs', function() {
  element.all(by.model('candidate.selected')).get(0).click();
  //expect(favorite.getText()).toContain('pizza');
});


$(window).load(function() {
    $(".bg_load").fadeOut("slow");
    $(".wrapper").fadeOut("slow");
})


