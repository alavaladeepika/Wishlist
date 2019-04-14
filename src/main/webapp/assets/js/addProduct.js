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
	
	//var product_id = $.urlParam("prodid");
	var product_id = $("#myModal").val();
	var login_id = checkCookie("login_id");
	
	console.log(product_id);
	
	if(login_id==null){
		window.location = "login.html";
	}
	
	var quantity = $('#quantity');
	var address = $('#address');
	var reason = $('#reason');
	
	var user_data = JSON.stringify({
		creator_id : login_id,
		
	});
	
	//Initial page setting
	dynamic_division();
	
	var url = "http://localhost:8080/WishlistService/webapi/creator/getAllWishlists";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data : user_data,
		success : function(wishlists){
			if(!$.isEmptyObject(wishlists)){
				//alert("Wishlists accessed!!");
				//var wishlists = data.wishlists;
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
		if($("#wishlist_type").val()=="old")
		   add_product_existing_wishlist();
		else if($("#wishlist_type").val()=="new")
		   add_product_new_wishlist();
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
		console.log(new_product);
        //alert("in render data  fn."+new_product.wishlist_id);
		$.ajax({url:"http://localhost:8080/WishlistService/webapi/creator/updateWishlistAdd",
			type:"POST",
			data: JSON.stringify(new_product),
			contentType: "application/json",
			async: true,
	 	    			
			success: function(data) {
				alert("success");
				if(data=="success"){
				     console.log(data);
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
				     console.log(data);
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