/**
 * 
 */
jQuery(document).ready(function($){
		var  req_data= {
				fullfiller_id 	: "vaishali",
				//sessionStorage.getItem("username"),
		};
		
		$.ajax({url:"http://localhost:8080/WishlistService/webapi/wishlist/getSharedWishlists", type:"POST",
			data: JSON.stringify(req_data),
			dataType: "json",
		 	async: true,
		 	
			success: function(data) {

				if(data.status=="success"){
					
					var wishlists = data.wishlists;
					var wishlist_no;
					
					//console.log(data);
					for(var i=0;i<wishlists.length;i++){
						wishlist_no=wishlists[i].wishlist_id;
						
						//console.log(wishlists[i].wishlist_name);
						var wishlist = 
								 "<tbody>"+
							        "<tr>"+
							          "<td>"+(i+1)+".</td>"+
							          "<td>"+
							            "<button id = 'btn_"+wishlist_no+"' type='button' class='btn btn-link'></button>"+
							          "</td>"+
							          "<td>"+wishlists[i].creator_id+"</td>"+
							          "<td>"+wishlists[i].creator_name+"</td>"+
							        "</tr>"+
							     "</tbody>";
						//console.log(data);
						$("#stable").append(wishlist);
						$("#btn_"+wishlist_no).text(wishlists[i].wishlist_name+"'s Wish List");
						
						$('#btn_'+wishlist_no).click(function(){
							//TODO
						});
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
		
});