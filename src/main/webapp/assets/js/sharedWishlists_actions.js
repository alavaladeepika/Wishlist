/**
 * Deepika
 */
jQuery(document).ready(function($){
	
	$("#header").load("header.html");
	
	var fullfiller_id = checkCookie("login_id");
	if(fullfiller_id==null){
		window.location = "login.html";
	}
	
	var url = "http://localhost:8080/WishlistService/webapi/fullfiller/getSharedWishlists/"+fullfiller_id;
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		success : function(wishlists){
			
			console.log(wishlists);
			if(!$.isEmptyObject(wishlists)){
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
						            "<button id = 'btn_"+wishlist_no+"' type='button' class='btn btn-link' style='color: #febd69; font-weight: bold;'></button>"+
						          "</td>"+
						          "<td>"+wishlists[i].creator_id+"</td>"+
						          "<td>"+wishlists[i].creator_name+"</td>"+
						        "</tr>"+
						     "</tbody>";
					//console.log(data);
					$("#stable").append(wishlist);
					$("#btn_"+wishlist_no).text(wishlists[i].wishlist_name+"'s Wish List");
					
					$('#btn_'+wishlist_no).click(function(){
						window.location = "sharedWishlist.html?wishlistid="+wishlist_no;
					});
				}
		    }
			else{
				alert("No wishlists shared with you.");
			}
		},
		error: function(wishlists) {
			alert("failed");
		}
	 	
	});
		
});