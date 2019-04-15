/**
 * Deepika
 */
jQuery(document).ready(function($){
	
	var login_id = checkCookie("login_id");
	if(login_id==null){
		$("#row2").hide();
	}
	else{
		$("#row2").show();
		$("#login").hide();
		$("#login_id").text("Hello, "+login_id);
	}
	
	$("body").on("click","#logo",function(){
		window.location = "catalog.html";
	});
	
	$("body").on("click","#logo_img",function(){
		window.location = "catalog.html";  //Add profile page
	});
	
	$("body").on("click","#login",function(){
		window.location = "login.html";
	});
	
	$("body").on("click","#login_id",function(){
		window.location = "#";  //Add profile page
	});
	
	$("body").on("click","#notifications",function(){
		window.location = "#";  //Add notifications page
	});
	
	$("body").on("click","#mywishlists",function(){
		window.location = "myWishlists.html"; 
	});
	
	$("body").on("click","#sharedlists",function(){
		window.location = "sharedWishlists.html";  
	});
	
	$("body").on("click","#placedorders",function(){
		window.location = "placedOrders.html";  
	});
	
	$("body").on("click","#logout",function(){
		deleteCookie("login_id");
		location.reload();
	});
	
});