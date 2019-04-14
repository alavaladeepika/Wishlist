package org.acms.WishlistService.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.acms.WishlistService.dao.CatalogDAO;
import org.acms.WishlistService.dao.CustomerDAO;
import org.acms.WishlistService.dao.OrderProductDAO;
import org.acms.WishlistService.dao.OrdersDAO;
import org.acms.WishlistService.dao.WishlistDAO;
import org.acms.WishlistService.dao.WishlistFullfillersDAO;
import org.acms.WishlistService.dao.WishlistProductDAO;
import org.acms.WishlistService.model.Customer;
import org.acms.WishlistService.model.OrderProduct;
import org.acms.WishlistService.model.Orders;
import org.acms.WishlistService.model.Wishlist;
import org.acms.WishlistService.model.WishlistFullfillers;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/order")
public class OrderServices {

	//To update order details when fullfiller buy the wishlist (Vaishali)
	@POST
	@Path("/buyWishlist")
	@Consumes("application/json")
	public String buyWishlist(String user_data) throws JSONException {
		
		JSONObject data = new JSONObject(user_data);
		
		//Get the wishlist fullfiller id
		WishlistFullfillers fullfiller = new WishlistFullfillers();
		fullfiller.setFullfiller_id(data.getString("fullfiller_id"));
		fullfiller.setWishlist_id(data.getInt("wishlist_id"));
		WishlistFullfillersDAO wfdao = new WishlistFullfillersDAO();
		int wishlistfullfiller_id = wfdao.getIDByDetails(fullfiller);
		
		CatalogDAO catalogDao = new CatalogDAO();
		WishlistProductDAO wpdao = new WishlistProductDAO();
		
		if(wishlistfullfiller_id!=-1) {
			
			//to add order
			Orders order = new Orders();
			order.setWishlistfullfiller_id(wishlistfullfiller_id);
			OrdersDAO ordersDao = new OrdersDAO();
			int order_id = ordersDao.addOrders(order);
			
			if(order_id!=-1) {
				
				JSONObject placed_orders = new JSONObject(data.getString("orders"));
				int count = data.getInt("count");
			
				for(int i=0; i<count; i++) {
					
					JSONObject objecti= new JSONObject(placed_orders.getString(Integer.toString(i)));
				
					
					OrderProduct product = new OrderProduct();
					product.setOrder_id(order_id);
					product.setProduct_id(objecti.getInt("product_id"));
					product.setQuantity(objecti.getInt("quantity"));
					product.setPrice((float)objecti.getDouble("price"));
					
					OrderProductDAO orderProductDao = new OrderProductDAO();
					
					if(orderProductDao.addOrderedProduct(product)==-1) {
						return "fail";
					}
					else {
						//Update Product quantity in catalog
						catalogDao.reduceQuantity(objecti.getInt("product_id"), objecti.getInt("quantity"));
						//Update remaining quantity in wishlist product
						wpdao.reduceRemainingQty(data.getInt("wishlist_id"), objecti.getInt("product_id"), objecti.getInt("quantity"));
					}
				}
				return sendNotificationEmail(data.getInt("wishlist_id"), data.getString("fullfiller_id"));
			}
			else {
				return "fail";
			}
		}
		
		return "fail";
	}
	
	//To send email notification that items are bought from the wishlist(Deepika)
	public String sendNotificationEmail(int wishlist_id, String fullfiller_id) {
		
		EmailServices email_serv = new EmailServices();
		
		//Get fullfiller emails
		WishlistFullfillersDAO wfdao = new WishlistFullfillersDAO();
		String[] fullfillers = wfdao.getFullfillersByWishlistID(wishlist_id);
		
		//Get creator email
		WishlistDAO wdao = new WishlistDAO();
		Wishlist wishlist = wdao.getWishlistDetailsByID(wishlist_id);
		CustomerDAO cdao = new CustomerDAO();
		Customer creator = cdao.getCustomerByID(wishlist.getCreator_id());
		
		//Add recipients
		String recipients[] = new String[fullfillers.length+1];
		
		recipients[0] = creator.getEmail_id();
		for(int i=1;i<fullfillers.length+1;i++) {
			recipients[i] = fullfillers[i-1];
		}
		
		if(recipients.length>0) {
			String subject = "Updated Wish list : Wish list Service";
	        String body = "<h2>Hello,</h3>"
	        			+ "<h3>"
	        				+ "Hope you are doing well. Some products from the wish list named '"
	        				+ wishlist.getName() + "', shared by '"+wishlist.getCreator_id()+"', have been purchased by '"+fullfiller_id+"'. "
	        				+ "Kindly open your account and fullfill the wishes of your loved ones."
	        			+ "</h3>"
	        			+ "<h4>Yours faithfully,</h4>"
	        			+ "<h4>Wish list Service Team.</h4>";
	        
	        return email_serv.sendEmail(recipients, subject, body);
		}
		return "success";
	}
		
}
