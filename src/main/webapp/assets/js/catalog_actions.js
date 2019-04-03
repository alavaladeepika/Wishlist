/**
 * Manisha
 */
jQuery(document).ready(function($){
			
		product_no = 0;
		
		$.ajax({url:"http://localhost:8080/WishlistService/webapi/catalog/getProducts",
			type:"GET",
			success: function(data) {
				if(!$.isEmptyObject(data)){
					
					console.log(data);
					var product_list = data;
					var start="<div class='container'>"+"<div class='row'>";
					
					$("body").append(start);
					for(var i=0;i<product_list.length;i++)
					{
						product_no++;
						var prod_name = product_list[i].product_name;
						var brand = product_list[i].brand;
						var description = product_list[i].description;
						var quantity = product_list[i].quantity;
						var price = product_list[i].price;
						var image_location = product_list[i].pic_location;
						
						var catalog= "<div class='col-sm-4'>"+
											"<a href='#'>"+
												"<img src="+image_location+" class='img-responsive' style='width:100%' alt='Image'>"+
											"</a>"+
											"<h3 align='center' class='title'>"+
												"<a href='#'>"+prod_name+"</a></h3>"+
											"<div class='brand' align='center'>"+"Brand: "+brand+
											"</div>"+
											"<div class='description' align='center'>"+"Description: "+description+
											"</div>"+
											"<div class='price' align='center'>"+"Price: Rs "+price+         
											"</div>"+
											"<button type='submit' class='btn add-to-cart btn-block'>Add to Wishlist</button>"+
										"</div>";
						$("body").append(catalog);
					}
					var last="</div></div>"
						$("body").append(last);
					
				}
				else{
					alert("failed not success");
				}
			},
			
			error: function(data) {
				alert("failed");
			}
			});
});