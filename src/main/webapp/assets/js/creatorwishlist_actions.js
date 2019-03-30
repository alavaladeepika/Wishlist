/**
 * 
 */
jQuery(document).ready(function($){
		var  req_data= {
				username 	: "deepika",
				//sessionStorage.getItem("username"),
				wishlist_id : "0",//get it from url parameters
		};
		
		//Initial page setting
		$.ajax({url:"http://localhost:8080/wishlist_service/webapi/myresource/getWishlist", type:"POST",
			data: JSON.stringify(req_data),
			dataType: "json",
		 	async: true,
		 	
			success: function(data) {

				if(data.status=="success"){
					
					$("#wname").text(data.wishlist_name+"'s Wish List");
					var items_list = data.items;
					var prod_no;
					
					for(var i=0;i<items_list.length;i++){
						prod_no=items_list[i].product_id;
						
						var product = 
							"<div id='prod_details_"+prod_no+"' class='col-lg-12' style='border: 5px solid #eee'>"+
							    "<div class='col-lg-4' style='padding: 5px'>"+
							      "<img id='prod_img_'"+prod_no+" src='images/catalog/book.jpg' alt='Card image' style='width:200px;height:200px;float:left' />"+
							    "</div>"+
							   
							    "<div>"+
							      "<form class='form-group' action='/action_page.php'>"+
								    "<div class='form-group' style='margin-left:250px'>"+
								      "<div class='form-group row' style='margin-bottom: 1px'>"+
								        "<label class='col-lg-2 col-form-label'>Product Name:</label>"+
								        "<label id='name_"+prod_no+"' class='col-lg-8 col-form-label'>Pen</label>"+
								      "</div>"+
								      "<div class='form-group row' style='margin-bottom: 1px'>"+
								        "<label class='col-lg-2 col-form-label'>Brand:</label>"+
								        "<label id='brand_"+prod_no+"' class='col-lg-8 col-form-label'>Benz</label>"+
								      "</div>"+
								      "<div class='form-group row' style='margin-bottom: 1px'>"+
								        "<label class='col-lg-2 col-form-label'>Price:</label>"+
								        "<label id='price_"+prod_no+"' class='col-lg-6 col-form-label'>Rs.100</label>"+
								        "<button id='edit_"+prod_no+"' type='button' class='col-lg-3 btn btn-warning' data-toggle='modal' data-target='#editModal_"+prod_no+"'>"+
								          "Edit Details</button>"+
								      "</div>"+
								      "<div class='form-group row' style='margin-bottom: 1px'>"+
								        "<label class='col-lg-2 col-form-label'>Quantity:</label>"+
								        "<label id='quantity_"+prod_no+"' class='col-lg-8 col-form-label'>3</label>"+
								      "</div>"+
								      "<div class='form-group row' style='margin-bottom: 1px'>"+
								        "<label class='col-lg-2 col-form-label'>Address:</label>"+
								        "<label id='address_"+prod_no+"' class='col-lg-8 col-form-label'>IIIT Bangalore</label>"+
								      "</div>"+
								      "<div class='form-group row' style='margin-bottom: 1px'>"+
								        "<label class='col-lg-2 col-form-label'>Reason:</label>"+
								        "<label id='reason_"+prod_no+"' class='col-lg-8 col-form-label'>For my birthday</label>"+
								      "</div>"+
								    "</div>"+
							      "</form>"+
							    "</div>"+
							"</div>";
						//console.log(items_list[i]);
						$("body").append(product);
						$("#prod_image_"+prod_no).attr("src",items_list[i].pic_location);
						$("#name_"+prod_no).text(items_list[i].product_name);
						$("#brand_"+prod_no).text(items_list[i].brand);
						$("#price_"+prod_no).text(items_list[i].price);
						$("#quantity_"+prod_no).text(items_list[i].quantity);
						$("#address_"+prod_no).text(items_list[i].address);
						$("#reason_"+prod_no).text(items_list[i].reason);
						$("#prod_details_"+prod_no).show();
						
						//Modal functionality
						onClickModalEdit(prod_no);
					}
			    }
				else{
					alert("Data could not be found");
				}
			},
			error: function(data) {
				alert("failed");
			}
		});
		
		//Edit button in the modal
		function onClickModalEdit(prod_no){
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
						      "<label class='col-form-label col-lg-3' for='qt'>Quantity:</label>"+
						      "<input class='form-control col-lg-7' id='edit_qt_"+prod_no+"' type='number' value='"+$('#quantity_'+prod_no).text()+"'>"+
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
				  $("body").append(modal);
			      $("#edit_"+prod_no).show();
			      console.log($('#reason_'+prod_no).text());
				  updateDetails(prod_no);
			});
		}
		
		//Update the details of the product in the wishlist
		function updateDetails(prod_no){
			$('#save_'+prod_no).click(function(){
				var init_qt = $("#quantity_"+prod_no).text();
				var new_qt = $("#edit_qt_"+prod_no).val();
				var init_address = $("#address_"+prod_no).text();
				var new_address = $("#edit_address_"+prod_no).val();
				var init_reason = $("#reason_"+prod_no).text();
				var new_reason = $("#edit_reason_"+prod_no).val();
				req_data["product_id"] = prod_no;
				var flag = 0;
				if(new_qt != init_qt){
					req_data["quantity"] =  new_qt;
					console.log(init_qt+" : "+new_qt);
					flag++;
				}
				if(new_address != init_address && new_qt !=0){
					req_data["address"] = new_address;
					console.log(init_address+" : "+new_address);
					flag++;
				}
				if(new_reason != init_reason && new_qt !=0){
					req_data["reason"] = new_reason;
					console.log(init_reason+" : "+new_reason);
					flag++;
				}
				if(flag>0){
					$.ajax({url:"http://localhost:8080/wishlist_service/webapi/myresource/updateWishlist", type:"POST",
						data: JSON.stringify(req_data),
						dataType: "json",
					 	async: true,
					 	
						success: function(data) {

							if(data.status=="success"){
								//console.log(data);
								$("#quantity_"+prod_no).text(new_qt);
								$("#address_"+prod_no).text(new_address);
								$("#reason_"+prod_no).text(new_reason);
							}
						},
						error: function(data) {
							alert("failed");
						}
					});
				}
			});
		}
		
		//delete wishlist
		$('#delete').click(function(){
			$.ajax({url:"http://localhost:8080/wishlist_service/webapi/myresource/deleteWishlist", type:"POST",
				data: JSON.stringify(req_data),
				dataType: "json",
			 	async: true,
			 	
				success: function(data) {

					if(data.status=="success"){
						alert("Wishlist successfully deleted!");
					}
				},
				error: function(data) {
					alert("failed");
				}
			});
		});
		
		
});