	function removeCK(cookie_name){
		var date = new Date();
		date.setTime(date.getTime() + (-1 * 60 * 60 * 1000));
		var expires = "; expires=" + date.toGMTString();
		document.cookie = cookie_name+ "=" + "" + expires + "; path=/ANBD"; 
		location.reload();
	}
	$(function(){
		  $('#back-to-top').on('click',function(e){
		      e.preventDefault();
		      $('html,body').animate({scrollTop:0},600);
		  });
		  
		  $(window).scroll(function() {
		    if ($(document).scrollTop() > 10) {
		      $('#back-to-top').addClass('show');
		    } else {
		      $('#back-to-top').removeClass('show');
		    }
		  });
		});