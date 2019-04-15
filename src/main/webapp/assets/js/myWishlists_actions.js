/**
 * Manisha
 */
jQuery(document).ready(function($){
			
	$("#header").load("header.html");
	var login_id = checkCookie("login_id");
	if(login_id==null){
		window.location = "login.html";
	}
	
	var data = {
		creator_id : login_id,
	};
	$.ajax({url:"http://localhost:8080/WishlistService/webapi/creator/getAllWishlists",
		data: JSON.stringify(data),
		type:"POST",
		contentType : 'application/json',		 	
		success: function(wishlists) {
			if(!$.isEmptyObject(wishlists)){
												
				var wishlist_no;
				
				for(var i=0;i<wishlists.length;i++){
					wishlist_no=wishlists[i].wishlist_id;
					var color = 'blue';
					if(wishlists[i].status=='INACTIVE'){
						color = 'red';
					}
					else if(wishlists[i].status=='FULLFILLED'){
						color = 'green';
					}

					var wishlist = 
							 "<tbody>"+
						        "<tr>"+
						          "<td>"+(i+1)+".</td>"+
						          "<td>"+
						            "<button id = 'btn_"+wishlist_no+"' type='button' class='btn btn-link' style='color: #febd69; font-weight: bold;' disabled></button>"+
						          "</td>"+
						          "<td style='color: "+color+";'>"+wishlists[i].status+"</td>"+
						          "<td>"+
						            "<button id = 'btn2_"+wishlist_no+"' type='button' class='btn btn-link' style='color: #febd69; font-weight: bold;' >Track Orders of Wish List</button>"+
						          "</td>"+
						        "</tr>"+
						     "</tbody>";
					$("#stable").append(wishlist);
					$("#btn_"+wishlist_no).text(wishlists[i].name+"'s Wish List");
						
					if(wishlists[i].status=='ONGOING'){
						$('#btn_'+wishlist_no).removeAttr('disabled');
					}	
					
					toWishlist(wishlist_no);
					toOrders(wishlist_no);
				}
			}
			else{
				alert("No wishlists available!");
			}
		},
		error: function(wishlists) {
			alert("failed");
		}
			 	
	});
	
	function toWishlist(id){
		$('#btn_'+id).click(function(){
			window.location = "creatorWishlists.html?wishlistid="+id;
		});
	}
	function toOrders(id){
		$('#btn2_'+id).click(function(){
			window.location = "viewOrders.html?wishlistid="+id;
		});
	}
});