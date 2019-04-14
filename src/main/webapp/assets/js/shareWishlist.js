/**
 * Vaishali 
 */
jQuery(document).ready(function($){
       
	//Get the url parameters
	$.urlParam = function(name){
	    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	    if (results==null) {
	       return null;
	    }
	    return decodeURI(results[1]) || 0;
	}
	
	$("#warning").hide();
	var wishlist_id = $.urlParam("wishlistid");
	var login_id = checkCookie("login_id");
	
	console.log(wishlist_id);
	
	if(login_id==null){
		window.location = "login.html";
	}
	
	$('body').on("click", '#share_wishlist', function(){

	    var req_data = {
			email: $('#email_id').val(),
			wishlist_id: wishlist_id, 
	    };
	    //console.log(req_data);
	    var url = "http://localhost:8080/WishlistService/webapi/creator/addWishlistFullfillers";
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : url,
			data : JSON.stringify(req_data),
			success : function(data){

				if(data=="success"){
					$("#warning").hide();
					//$('#myModal').hide();
					//$('.window').hide();
					location.reload();
				    alert("Successfully Shared!");
			    }
				else if(data=="email_fail"){
					$("#warning").show();
				}
			    else{
			    	$("#warning").hide();
			    	alert("Internal error!");
				}
			},
			
			error: function(data) {
				//console.log("failed");
				alert("failed");
			}
		   
		});
	});
	
});