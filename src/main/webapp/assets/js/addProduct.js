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
	
	var product_id = $("#myModal").val();
	var login_id = checkCookie("login_id");
	
	
	if(login_id==null){
		window.location = "login.html";
	}
	
	var quantity = $('#quantity');
	var address = $('#address');
	var reason = $('#reason');
	
	//Initial page setting
	dynamic_division();
	
	var user_data = JSON.stringify({
		creator_id : login_id,
		status : "ONGOING",
		
	});

	var url = "http://localhost:8080/WishlistService/webapi/creator/getOngoingWishlists";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : user_data,
		success : function(wishlists){
			if(!$.isEmptyObject(wishlists)){
				for(var i=0;i<wishlists.length;i++){
					$("#selected_wishlist").append("<option value='"+wishlists[i].wishlist_id+"'>"+wishlists[i].name+"</option>");	
				}	
			}
		},
		error: function(wishlists) {
			alert("failed");
		}
	});
	
	$("#wishlist_type").change(function(){
		dynamic_division();
	});
	
	$('#add_product_btn').click(function(){
		if($("#wishlist_type").val()=="old"){
			add_product_existing_wishlist();
		}
		else if($("#wishlist_type").val()=="new"){
		   add_product_new_wishlist();
		}
	});
		
	function add_product_existing_wishlist(){
		new_product = {
		    wishlist_id : $('#selected_wishlist').val(),
		    product_id : product_id,
			quantity : quantity.val(),
			remaining_qty : quantity.val(),
			address : address.val(),
			reason : reason.val(),
		}

		$.ajax({url:"http://localhost:8080/WishlistService/webapi/creator/updateWishlistAdd",
			type:"POST",
			data: JSON.stringify(new_product),
			contentType: "application/json",
			async: true,
	 	    			
			success: function(data) {
				if(data=="success"){
					 alert("success");
				     location.reload();
				}
				else if(data=="ProductAlreadyExists"){
					alert("The product already exists in the selected wish list.");
					location.reload();
				}
			    else{
				     alert("failed not success");
			    }
			}
		});
		
	}
			
    function add_product_new_wishlist(){
			
		new_product = {
 		    creator_id : login_id, 
 		    product_id : product_id,  
 		    name : $('#name').val(),
 			quantity : quantity.val(),
 			remaining_qty : quantity.val(),
 			address : address.val(),
 			reason : reason.val(),
 			status : "ONGOING",
		}

		$.ajax({url:"http://localhost:8080/WishlistService/webapi/creator/createWishlistAddProduct",
			type:"POST",
			data: JSON.stringify(new_product),
			contentType: "application/json",
			async: true,
	 	    
			success: function(data) {
				if(data=="success"){
				     location.reload();
				}
			    else{
				     alert("failed not success");
			    }
					
			}
		});
		
	}
         
    function dynamic_division(){
       	if($("#wishlist_type").val()=="new"){
			$("#new_wishlist_name").show();
			$("#existing_wishlist").hide();
		}
		else if($("#wishlist_type").val()=="old"){
			$("#existing_wishlist").show();
			$("#new_wishlist_name").hide();
		}
		else{
			$("#new_wishlist_name").hide();
			$("#existing_wishlist").hide();
		}
    }
			
});