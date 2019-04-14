/**
 * Deepika
 */
jQuery(document).ready(function($){
	
	$("#header").load("header.html");
	
	//Get the url parameters
	$.urlParam = function(name){
	    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	    if (results==null) {
	       return null;
	    }
	    return decodeURI(results[1]) || 0;
	}
	
	var wishlist_id = $.urlParam("wishlistid");
	var login_id = checkCookie("login_id");
	
	if(login_id==null){
		window.location = "login.html";
	}
	
	//Initial page setting
	var url = "http://localhost:8080/WishlistService/webapi/creator/getCreatorWishlistDetails/"+wishlist_id;
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		success : function(items){
			
			console.log(items);
			if(!$.isEmptyObject(items)){
				$("#wname").text(items[0].wishlist_name+"'s Wish List");

				var prod_no;
				
				for(var i=0;i<items.length;i++){
					prod_no=items[i].id;
					
					var product = 
						"<div id='prod_details_"+prod_no+"' class='col-lg-12' style='border: 2px solid #eee'>"+
						    "<div class='col-lg-4' style='padding: 5px'>"+
						      "<img id='prod_img_"+prod_no+"' src='#' alt='Card image' style='margin-top:30px;margin-left:25px;width:200px;height:200px;float:left;'/>"+
						    "</div>"+
						   
						    "<div>"+
							   "<div class='form-group' style='margin-left:320px'>"+
							      "<div><h4 id='name_"+prod_no+"' class='card-title'>Pen</h4></div>"+
							      "<div><label id='price_"+prod_no+"' class='col-form-label' style='font-weight: bold; color: #800000; font-size: 20px;'>Rs.100</label></div>"+
							      "<div><label id='quantity_"+prod_no+"' class='col-form-label' style='color: green; font-size: 15px;'>3</label></div>"+
					              "<div class='row'>" +
					              	"<label id='fqty_"+prod_no+"' class='col-lg-7 col-form-label' style='color: blue; font-size: 15px;'>3</label>" +
					              	"<button id='edit_"+prod_no+"' type='button' class='col-lg-3 btn amazon_btn' data-toggle='modal' data-target='#editModal_"+prod_no+"'>"+
				                    "Edit Details</button>"+
					              "</div>"+
							      "<div><label id='brand_"+prod_no+"' class='col-form-label' style='color: gray; font-size: 14px;'>Brand: </label></div>"+
							      "<div class='col-lg-4 row'>"+
							      	 "<label class='col-form-label' style='color: gray; font-size: 14px;'>Address: </label>"+
							      	 "<label id='address_"+prod_no+"' class='col-form-label' style='margin-left: 5px; color: gray; font-size: 14px;'></label>" +
							      "</div>"+
							      "<div class='col-lg-4 row'>"+
							      	 "<label class='col-form-label' style='color: gray; font-size: 14px;'>Reason: </label>"+
							      	 "<label id='reason_"+prod_no+"' class='col-form-label' style='margin-left: 5px; color: gray; font-size: 14px;'></label>" +
							      "</div>"+
							   "</div>"+
						    "</div>"+
						"</div>";
					//console.log(items_list[i]);
					$("#prod_details").append(product);
					$('#prod_img_'+prod_no).attr("src",items[i].pic_location);
					$("#name_"+prod_no).text(items[i].product_name);
					$("#brand_"+prod_no).text("Brand: "+items[i].brand);
					$("#price_"+prod_no).text("Rs. "+items[i].price);
					$("#quantity_"+prod_no).text("Required: "+items[i].quantity+" unit(s)");
					$("#fqty_"+prod_no).text("Fullfilled: "+items[i].fullfilled_qty+" unit(s)");
					$("#address_"+prod_no).text(items[i].address);
					$("#reason_"+prod_no).text(items[i].reason);
					$("#prod_details_"+prod_no).show();
					
					//Modal functionality
					onClickModalEdit(prod_no, items[i].quantity, items[i].fullfilled_qty);
				}
		    }
			else{
				//alert("No products added to the wish list");
				var no_items = "<div id='no_items' align='center' style='border: 2px solid #eee;'>"+
								  "<label class='col-form-label'>You have no items in this wishlist.</label>"+
							   "</div>";
				$("#prod_details").append(no_items);
				$("#no_items").show();
			}
		},
		error: function(items) {
			alert("failed");
		}
	});
		
	//Edit button in the modal
	function onClickModalEdit(prod_no, qty, f_qty){
		$('#edit_'+prod_no).click(function(){
			var modal = 
			 "<div class='modal fade' id='editModal_"+prod_no+"'>"+
				"<div class='modal-dialog modal-lg modal-dialog-centered'>"+
				  "<div class='modal-content'>"+
				    "<div class='modal-header'>"+
					  "<h4 class='modal-title'>Edit Details:</h4>"+
					  "<button type='button' class='close' data-dismiss='modal'>&times;</button>"+
				    "</div>"+
					"<div class='modal-body' align='center'>"+
					  "<form class='form-group' action='/action_page.php'>"+
				  	    "<div class='form-group row'>"+
					      "<label class='col-form-label col-lg-3' for='qt'>Required Quantity:</label>"+
					      "<input class='form-control col-lg-7' id='edit_qt_"+prod_no+"' type='number' value='"+qty+"'>"+
					    "</div>"+
					    "<div class='form-group row'>"+
					      "<label class='col-form-label col-lg-3' for='address'>Address:</label>"+
					      "<input type='text' class='form-control col-lg-7' maxlength='100' id='edit_address_"+prod_no+"' value='"+$('#address_'+prod_no).text()+"'>"+
					    "</div>"+
					    "<div class='form-group row'>"+
					      "<label class='col-form-label col-lg-3' for='reason'>Reason:</label>"+
					      "<input type='text' class='form-control col-lg-7' maxlength='100' id='edit_reason_"+prod_no+"' value='"+$('#reason_'+prod_no).text()+"'>"+
					    "</div>"+
					  "</form>"+
					"</div>"+
					"<div class='modal-footer'>"+
					  "<button id='save_"+prod_no+"' type='button' class='col-lg-3 btn btn-success btn-lg' data-dismiss='modal'>Save Details</button>"+
					"</div>"+
					  "</div>"+
				"</div>"+
			  "</div>";
			  $("#editModals").append(modal);
		      $("#edit_"+prod_no).show();
		      console.log($('#reason_'+prod_no).text());
			  updateDetails(prod_no, qty, f_qty);
		});
	}
	
	//Update the details of the product in the wishlist
	function updateDetails(prod_no, qty, f_qty){
		//console.log(f_qty+"..");
		$('#save_'+prod_no).click(function(){
			var init_qt = qty;
			var new_qt = $("#edit_qt_"+prod_no).val();
			var init_address = $("#address_"+prod_no).text();
			var new_address = $("#edit_address_"+prod_no).val();
			var init_reason = $("#reason_"+prod_no).text();
			var new_reason = $("#edit_reason_"+prod_no).val();
			
			var wishlist_product = {
				id : prod_no,
			}; 
			
			if(new_qt != init_qt){
				wishlist_product["quantity"] =  new_qt;
				wishlist_product["remaining_qty"] = new_qt-f_qty;
				console.log(init_qt+" : "+new_qt);
			}

			if(new_address != init_address){
				wishlist_product["address"] = new_address;
				console.log(init_address+" : "+new_address);
			}
			
			if(new_reason != init_reason){
				wishlist_product["reason"] = new_reason;
				console.log(init_reason+" : "+new_reason);
			}
			
			var url = "http://localhost:8080/WishlistService/webapi/creator/updateWishlist/"+login_id;
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : url,
				data : JSON.stringify(wishlist_product),
				success : function(data){
			
					if(data=="success"){
						location.reload();
					}
				},
				error: function(data) {
					alert("failed");
				}
			});
		});
	}
	
	//delete wishlist
	$('body').on('click', '#delete', function(){
		var wishlist = {
			wishlist_id : wishlist_id,
			status : 'INACTIVE',
		}
		var url = "http://localhost:8080/WishlistService/webapi/creator/deleteWishlist";
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : url,
			data : JSON.stringify(wishlist),
			success : function(data){
				
				console.log(data);
				if(data=="success"){
					alert("Wishlist successfully deleted!");
					//Goto all wishlists page
					window.location = "myWishlists.html";
				}
			},
			error: function(data) {
				alert("failed");
			}
		});
	});

	//share wishlist
	$('body').on('click', '#share', function(){
		$('#shareWishlist').load("shareWishlist.html?wishlistid="+wishlist_id,function(){
            $('#myModal').modal({show:true});
        });
		//location.reload();
	});

		
});