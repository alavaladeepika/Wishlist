/**
 * Manisha
 */
jQuery(document).ready(function($){
			
	$("#header").load("header.html");
	//$("#addProduct").load("addProduct.html");
	
	$.ajax({url:"http://localhost:8080/WishlistService/webapi/catalog/getProducts",
		type:"GET",
		success: function(data) {
			if(!$.isEmptyObject(data)){
				
				console.log(data);
				var product_list = data;
				for(var i=0;i<product_list.length;i++)
				{
					var prod_id = product_list[i].product_id;
					var prod_name = product_list[i].product_name;
					var brand = product_list[i].brand;
					var description = product_list[i].description;
					var quantity = product_list[i].quantity;
					var price = product_list[i].price;
					var image_location = product_list[i].pic_location;
					
					var catalog= "<div class='card' style='width:326px; height: 430px; border-radius: 0 !important;'>"+
									"<img src="+image_location+" class='card-img-top zoom'>"+
									"<div class='card-body' align='center'>"+
										"<h4 class='card-title' style='color: blue;'>"+prod_name+"</h4>"+
										"<div><label class='col-form-label' style='font-weight: bold; color: #800000;'>Price: Rs. "+price+"</label></div>"+
										"<div><label class='col-form-label' style='font-size: 12px; color: gray;'>"+"Brand: "+brand+"</label></div>"+
										"<div><label class='col-form-label' style='font-size: 12px; color: gray;'>"+"Description: "+description+"</label></div>"+
										"<button type='button' id='submit_"+prod_id+"'class='btn amazon_btn' data-href='addProduct.html?prodid="+prod_id+"' data-toggle='modal' data-target='#myModal'>Add to Wish List</button>"+
									"</div>"+
								 "</div>";
					$("#catalog").append(catalog);
					onAddProduct(prod_id);
				}
			}
			else{
				alert("failed not success");
			}
		},
		
		error: function(data) {
			alert("failed");
		}
	});
	
	function onAddProduct(id){
		var product_id = id;
		$('body').on("click", "#submit_"+id, function(){
			//$("#addProduct").load("addProduct.html?prod_id="+prod_id);
			var dataURL = $(this).attr('data-href');
			console.log(dataURL);
			//window.location = dataURL;
	        $('#addProduct').load("addProduct.html",function(){
	        	$("#myModal").val(product_id);
	            $('#myModal').modal({show:true});
	        });
		});
	}
});