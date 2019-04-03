/**
 * Manisha
 */
	jQuery(document).ready(function($){
			
			wishlist_no = 0;
			var Data={  creator_id : "deepika"//sessionStorage.getItem("user_id") 
				
				};
			$.ajax({url:"http://localhost:8080/WishlistService/webapi/creator/getAllWishlists",
				data: JSON.stringify(Data),
				type:"POST",
				contentType : 'application/json',		 	
				success: function(wishlists) {
					if(!$.isEmptyObject(wishlists)){
												
						var wishlist_no;
				
						for(var i=0;i<wishlists.length;i++){
							wishlist_no=wishlists[i].wishlist_id;

							var wishlist = 
									 "<tbody>"+
								        "<tr>"+
								          "<td>"+(i+1)+".</td>"+
								          "<td>"+
								            "<button id = 'btn_"+wishlist_no+"' type='button' class='btn btn-link'></button>"+
								          "</td>"+
								          "<td>"+wishlists[i].status+"</td>"+
								        "</tr>"+
								     "</tbody>";
							$("#stable").append(wishlist);
							$("#btn_"+wishlist_no).text(wishlists[i].name+"'s Wish List");
							
							$('#btn_'+wishlist_no).click(function(){
								//TODO
							});
						}
					}
					else{
						alert("failed not success");
					}
				},
				error: function(wishlists) {
					alert("failed");
				}
			 	
				});
	});