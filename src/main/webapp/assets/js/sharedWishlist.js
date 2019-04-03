/**
 * Vaishali
 */
jQuery(document).ready(function($){
	var  user_data= {
		//username 	: "vaishali", //sessionStorage.getItem("username"),
		wishlist_id : "1",       //get it from url parameters
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
			//if(data.status=="success"){
				$("#wname").text(items_list[0].wishlist_name+"'s Wish List");
				var prod_no;
					
				for(var i=0;i<items_list.length;i++){
					prod_no=items_list[i].product_id;
					var reason=items_list[i].reason;
			        var img_location = items_list[i].pic_location;
					var product_name=items_list[i].product_name;
					var brand=items_list[i].brand;
					var description=items_list[i].description;
					var price=items_list[i].price;
					var quantity=items_list[i].quantity;
					var address=items_list[i].address;
						
					var product=
						"<div id='prod_details_"+prod_no+"' class='col-lg-12' style='border: 5px solid #eee'>"+
							"<div class='col-lg-6' style='padding: 5px'>"+
							    "<img id='prod_img_'"+prod_no+" src='"+img_location+"' alt='Card image' style='width:200px;height:200px;float:left' />"+
							"</div>"+
							   
							"<div>"+
							   "<form class='form-group' action='/action_page.php'>"+
								    "<div class='form-group' style='margin-left:250px'>"+
								        "<div class='form-group row' style='margin-bottom: 1px'>"+
								            "<label class='col-lg-2 col-form-label'>Product Name:</label>"+
								            "<label id='name_"+prod_no+"' class='col-lg-8 col-form-label'>"+product_name+"</label>"+
								        "</div>"+
								        "<div class='form-group row' style='margin-bottom: 1px'>"+
								            "<label class='col-lg-2 col-form-label'>Brand:</label>"+
								            "<label id='brand_"+prod_no+"' class='col-lg-8 col-form-label'>"+brand+"</label>"+
								        "</div>"+
								        "<div class='form-group row' style='margin-bottom: 1px'>"+
								            "<label class='col-lg-2 col-form-label'>Description:</label>"+
								            "<label id='description_"+prod_no+"' class='col-lg-8 col-form-label'>"+description+"</label>"+
								        "</div>"+
								        "<div class='form-group row' style='margin-bottom: 1px'>"+
								            "<label class='col-lg-2 col-form-label'>Price:</label>"+
								            "<label id='price_"+prod_no+"' class='col-lg-6 col-form-label'>"+price+"</label>"+
								        "</div>"+
								        "<div class='form-group row' style='margin-bottom: 1px'>"+
								            "<label class='col-lg-2 col-form-label'>Quantity:</label>"+
								            "<input type='number' id='quantity_"+prod_no+"' class='col-lg-1 col-form-label' min=0 max="+quantity+" value="+quantity+"></label>"+
								        "</div>"+
								        "<div class='form-group row' style='margin-bottom: 1px'>"+
								            "<label class='col-lg-2 col-form-label'>Address:</label>"+
								            "<label id='address_"+prod_no+"' class='col-lg-8 col-form-label'>"+address+"</label>"+
								        "</div>"+
								        "<div class='form-group row' style='margin-bottom: 1px'>"+
								            "<label class='col-lg-2 col-form-label'>Reason:</label>"+
								            "<label id='reason_"+prod_no+"' class='col-lg-8 col-form-label'>"+reason+"</label>"+
								        "</div>"+
								    "</div>"+
							    "</form>"+
							"</div>"+
						"</div>";
						
						$("body").append(product);
				}
			}
			else{
				alert("Data could not be found");
			}
		},
		error: function(items_list) {
			alert("failed");
		}
	});
		
	
	//buy wishlist
	//For now only alert message of successful payment is being displayed
	$('#buy').click(function(){
		alert("Payment Successful!!");
	});
			
});