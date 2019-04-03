/**
 * Vaishali 
 */
	jQuery(document).ready(function($){
       
		$('#share').click(function(){
		
		    var req_data = {
				fullfiller_id: $('#email_id').val(),
				wishlist_id: 1, //current wishlist id which has been selected
		    };

		    var url = "http://localhost:8080/WishlistService/webapi/creator/addWishlistFullfillers";
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : url,
				data : JSON.stringify(req_data),
				
				success : function(data){
				    if(data=="success"){
					    alert("Successfully Shared");
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
		
	});