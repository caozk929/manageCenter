(function($){ 
  $(document).ready(function(){
    // Go to Top
    $('.goto-box .fa-top').on('click',function(e){
      $('html, body').animate({ scrollTop: 0 }, 500);
      return false;
    });

    // Select Box
    $('.form-list select').each(function(i){
		  $(this).selectbox();
	  });

  	// Radio
  	$('.form-list .input-radio').each(function(i){
  	  $(this).iCheck();
  	});

    //Checkbox
    $('.form-list .input-checkbox').each(function(i){
      $(this).iCheck();
    });

    //Datepicker
    $('.form-list .datepicker').each(function(i){
      $(this).datepicker({
        showOn: 'button',
        buttonImage: 'images/bg-calendar.gif',
        buttonImageOnly: true,
        buttonText: '选择日期',
        dateFormat: 'yy-mm-dd'
      });
    }); 
  });
})(jQuery);


