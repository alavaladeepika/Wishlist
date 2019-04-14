/**
 * Vaishali
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
	var  user_data= {
		wishlist_id : wishlist_id,       
	};

	//Initial page setting
	var url = "http://localhost:8080/WishlistService/webapi/fullfiller/getWishlist";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		data: JSON.stringify(user_data),
		 	
		success: function(items_list) {
			if(!$.isEmptyObject(items_list)){
				$("#wname").text(items_list[0].wishlist_name+"'s Wish List");
				var prod_no;
				var ids_list = new Array(items_list.length);
				var qtys = new Array(items_list.length);
				
				for(var i=0;i<items_list.length;i++){
					prod_no=items_list[i].product_id;
					ids_list[i] = prod_no;
					var reason=items_list[i].reason;
			        var img_location = items_list[i].pic_location;
					var product_name=items_list[i].product_name;
					var brand=items_list[i].brand;
					var description=items_list[i].description;
					var price=items_list[i].price;
					var quantity=items_list[i].remaining_qty;
					var req_qty = items_list[i].req_qty;
					if(quantity<=0){
						quantity = 0;
					}
					var prod_qty=items_list[i].product_qty;
					qtys[i]=prod_qty;
					var address=items_list[i].address;
						
					var product=
						"<div id='prod_details_"+prod_no+"' class='col-lg-12' style='border: 2px solid #eee'>"+
							"<div class='row'>"+
							    "<div class='col-lg-4' style='padding: 5px'>"+
							      "<img id='prod_img_"+prod_no+"' src='"+img_location+"' alt='Card image' style='margin-top:6px;margin-left:25px;width:200px;height:200px;float:left;'/>"+
							    "</div>"+
							   
							    "<div>"+
								    "<div style='margin-left: 30px; margin-top: 20px;'>"+
								      "<div><h4 class='card-title'>"+product_name+"</h4></div>"+
								      "<div><label id='brand_"+prod_no+"' class='col-form-label' style='color: gray; font-size: 14px;'>Brand: "+brand+"</label></div>"+
								      "<div><label id='address_"+prod_no+"' class='col-form-label' style='color: gray; font-size: 14px;'>Address: "+address+"</label></div>"+
								      "<div><label id='reason_"+prod_no+"' class='col-form-label' style='color: gray; font-size: 14px;'>Reason: "+reason+"</label></div>"+
								      "<div><label id='description_"+prod_no+"' class='col-form-label' style='color: gray; font-size: 14px;'>Description: "+description+"</label></div>"+
								   "</div>"+
							    "</div>"+
							    
							    "<div>"+
							    	"<div style='margin-top: 30px; margin-left: 250px;'>"+
								    	"<div class='row'>" +
								      		"<label class='col-form-label' style='font-weight: bold; color: #800000; font-size: 20px;'>Rs. </label>" +
								      		"<label id='price_"+prod_no+"' class='col-form-label' style='font-weight: bold; color: #800000; margin-left: 4px; font-size: 20px;'>"+price+"</label>" +
								      	"</div>"+
								      	
								      	"<div class='row'>" +
								      		"<label class='col-form-label' style='color: green; font-size: 15px; margin-right: 10px;'>Required: </label>" +
								      		"<label id='req_qty_"+prod_no+"' class='col-form-label' style='color: green; font-size: 15px;'>"+req_qty+" unit(s)</label>" +
								      	"</div>"+
								      
								      	"<div class='row'>"+
								      		"<label class='col-form-label' style='color: red; font-size: 15px; margin-right: 10px;'>Remaining: </label>"+
								      		"<input type='number' id='quantity_"+prod_no+"' class='col-form-label' min=0 max="+quantity+" value="+quantity+"></input>"+
								      	"</div>"+
							    	"</div>"+
							    "</div>"+
							"</div>"+
						"</div>";
					              
					              
						$("#prod_details").append(product);
				}
				toBuy(ids_list, qtys);
			}
			else{
				//alert("No products added to the wish list");
				var no_items = "<div id='no_items' align='center' style='border: 2px solid #eee;'>"+
								  "<label class='col-form-label'>You have no items in this wishlist.</label>"+
							   "</div>";
				$("#prod_details").append(no_items);
				$("#no_items").show();
				$("#buy").attr("disabled", "disabled");
			}
		},
		error: function(items_list) {
			alert("failed");
		}
	});
		
	
	//buy wishlist
	//For now only alert message of successful payment is being displayed
	function toBuy(ids, qtys){
		$('body').on("click", '#buy', function(){
			//alert("Payment Successful!!"+item_list[0].wishlist_name+item_list.length);
			
			var user_data = {
				"wishlist_id" : wishlist_id,
				"fullfiller_id" : login_id,
			};
			
			var order_details = {};
			var count = 0;
			var flag = 0;
			
			for(var i=0; i<ids.length; i++){
				
				var prod_no = ids[i];
				console.log(prod_no);
				if(qtys[i]< $('#quantity_'+prod_no).val()){
					alert("Only "+qtys[i]+" unit(s) of the product " + (i+1) +" are available!");
					flag=1;
					break;
				}
				else if($('#quantity_'+prod_no).val() > 0){
					var product = {};
					product["product_id"] = prod_no;
					product["quantity"] = $('#quantity_'+prod_no).val();
					product["price"] = $('#price_'+prod_no).text() * $('#quantity_'+prod_no).val();
					
					order_details[count.toString()] = JSON.stringify(product);
					//alert("#price_"+prod_no+" "+product["price"]);
					count+=1;
				}
			}
			
			if(flag==0 && count>0){
			
				user_data["orders"] = JSON.stringify(order_details); 
				user_data["count"] = count;
				
				var url = "http://localhost:8080/WishlistService/webapi/order/buyWishlist";
				$.ajax({
					type : 'POST',
					contentType : 'application/json',
					url : url,
					data: JSON.stringify(user_data),
					
					success: function(data) {
						if(data == "success"){
						    //alert("Added Successfully");
							window.location = "PaymentDone.html";
														
						}
						else{
							//alert("Data could not be found");
						}
					},
					error: function(data) {
						alert("failed"+data);
					}
				
			     });
			}
		});	
	}
});