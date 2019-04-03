/**
 * Manisha
 */
var username;
var password;
jQuery(document).ready(function($){
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
					sessionStorage.setItem("user_id",String(uid));
					//alert(sessionStorage.getItem("user_id"));
					window.location.replace("http://localhost:8080/WishlistService/catalog.html");
					//$("#prod_seller").text("Seller: "+seller.name);
				}
				else{
					alert("Invalid User ID or Password");
				}
			},
			
		});
	}
});