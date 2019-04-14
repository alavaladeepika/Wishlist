/**
 * Manisha
 */
var username;
var password;
jQuery(document).ready(function($){
	$("body").on("click","#logo_img",function(){
		window.location = "catalog.html";
	});
	
	$("body").on("click","#register",function(){
		window.location = "register.html";
	});
	
	$('#Login').click(function(){
		
		render_data();
				
	});
	
	function render_data(){
		
		value = {
				login_id : $('#username').val(),
				password : $('#password').val(),		
		}
		get_data(value);
	}
	function get_data(value)
	{
		$.ajax({url:"http://localhost:8080/WishlistService/webapi/customer/authenticate",
			data: JSON.stringify(value),
			type:"POST",
			contentType : 'application/json',
			async: true,
	 	
			success: function(data) {
				if(!$.isEmptyObject(data)){
					alert ("Login successfully");
					var uid=$("#username").val();
					setCookie("login_id", uid, 1);
					window.location = "catalog.html";
				}
				else{
					alert("Invalid User ID or Password");
				}
			},
			
		});
	}
});