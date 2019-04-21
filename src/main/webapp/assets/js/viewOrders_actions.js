/**
 * Manisha
 */
jQuery(document).ready(function($) {
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

	if (login_id == null) {
		window.location = "login.html";
	}

	// Initial page setting
	var url = "http://localhost:8080/WishlistService/webapi/order/getOrdersOfWishlist/"+ wishlist_id;
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				for (var i = 0; i < result.length; i++) {
					var fullfiller_orders = result[i];
					var fullfiller = "<div id='fullfiller_"+i+"' class='col-lg-12' style='border: 2px solid #eee'>"+
										"<div><h5 align='center' class='card-title' style='margin-top: 8px;'>FULFILLER DETAILS: "+fullfiller_orders[0][0].fullfiller_name+" ("+fullfiller_orders[0][0].fullfiller_id+")</h5></div>"+
									 "</div>";
					$("#prod_details").append(fullfiller);
					
					for (var j=0;j<fullfiller_orders.length;j++){
						var amt_paid = 0
						var order_details = fullfiller_orders[j];
						var orders = "<div id='orders_"+i+"_"+j+"' style='border: 1px solid #eee; margin-top: 10px;'>" +
										"<h6 class='card-title' style='margin-left: 20px; color: gray; font-weight: bold; margin-top: 10px;'>ORDER ID: "+order_details[0].order_id+"</h6>" +
									 "</div>";
						$("#fullfiller_"+i).append(orders);
						
						for(var k=0;k<order_details.length;k++){
							var item = order_details[k];
							
							amt_paid+=item.price;
							var product = "<div class='col-lg-12' style='border-top: 1px solid #eee'>"
											+ "<div class='col-lg-4' style='padding: 5px'>"
												+ "<img src='"+item.pic_location+"' alt='Card image' style='margin-top:15px;margin-left:25px;width:200px;height:180px;float:left;'/>"
											+"</div>"
											
											+"<div>"
												+ "<div class='form-group' style='margin-left:400px'>"
													+ "<div><h4 class='card-title'>"+item.product_name+"</h4></div>"
													+ "<div><label class='col-form-label' style='color: #800000; font-size: 18px;'>Amount Paid: Rs. "+item.price+"</label></div>"
													+ "<div><label class='col-form-label' style='color: green; font-size: 15px;'>Purchased Quantity: "+item.quantity+" unit(s)</label></div>"
													+ "<div><label class='col-form-label' style='color: gray; font-size: 14px;'>Brand: "+item.brand+"</label></div>"
													+ "<div><label class='col-form-label' style='color: gray; font-size: 14px;'>Description: "+item.description+"</label></div>"
												+ "</div>"
											+ "</div>" 
										+ "</div>";
							$("#orders_"+i+"_"+j).append(product);
						}
						var summary = "<div style='border-top: 1px solid #eee;'><h6 class='card-title' align='center' style='margin-top: 10px; font-weight: bold; color: #febd69;'>TOTAL AMOUNT PAID: Rs. "+amt_paid+"</h6></div>";
						$("#orders_"+i+"_"+j).append(summary);
						$("#orders_"+i+"_"+j).show();
						$("#no_items").hide();
					}
				}
			} 
			else {
				var no_items = "<div id='no_items' align='center' style='border: 2px solid #eee;'>"
								+ "<label class='col-form-label'>No orders placed for this wishlist yet.</label>"
				         	 + "</div>";
				$("#prod_details").append(no_items);
				$("#no_items").show();
			}
		},
		error : function(items) {
			alert("failed");
		}
	});
});