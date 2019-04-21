package org.acms.WishlistService.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.acms.WishlistService.dao.CatalogDAO;
import org.acms.WishlistService.dao.CustomerDAO;
import org.acms.WishlistService.dao.OrderProductDAO;
import org.acms.WishlistService.dao.OrdersDAO;
import org.acms.WishlistService.dao.WishlistDAO;
import org.acms.WishlistService.dao.WishlistFullfillersDAO;
import org.acms.WishlistService.dao.WishlistProductDAO;
import org.acms.WishlistService.model.Catalog;
import org.acms.WishlistService.model.Customer;
import org.acms.WishlistService.model.OrderProduct;
import org.acms.WishlistService.model.Orders;
import org.acms.WishlistService.model.Wishlist;
import org.acms.WishlistService.model.WishlistFullfillers;
import org.json.JSONArray;
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
		
		if(fullfillers!=null) {
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
		
	
	// API to get details of orders placed for a wishlist. (Manisha)
	@POST
	@Path("/getOrdersOfWishlist/{id}")
	@Produces("application/json")
	public String getOrdersOfWishlist(@PathParam("id") int wishlist_id) throws JSONException{
		WishlistFullfillersDAO dao = new WishlistFullfillersDAO();
		OrderProductDAO odao = new OrderProductDAO();
		CustomerDAO cudao=new CustomerDAO();
		OrdersDAO wdao = new OrdersDAO();
		CatalogDAO cdao = new CatalogDAO();
		
		List<WishlistFullfillers> wishlist_fullfillers = dao.getDetailsByWishlistID(wishlist_id);
		if(wishlist_fullfillers != null){
			JSONArray result = new JSONArray();
			for (int i = 0; i < wishlist_fullfillers.size(); i++) {
				//Getting the fullfillers with the wishlist id
				Customer c=cudao.getCustomerByID(wishlist_fullfillers.get(i).getFullfiller_id());
				String name=c.getName();
				String cust_id = c.getLogin_id();
				
				//Getting the order ids given the wishlist fullfiller id
				ArrayList<Integer> order_ids = wdao.getOrdersByID(wishlist_fullfillers.get(i).getId());
				if (order_ids!=null) {
					if(order_ids.size() != 0) {
						JSONArray fullfiller_orders = new JSONArray();
						for (int j = 0; j < order_ids.size(); j++) {
							JSONArray order_details = new JSONArray();
							
							//Getting the products purchased in the given order 
							List<OrderProduct> op = odao.getOrderDetailsByOrderID(order_ids.get(j));
							
							for(int k=0;k<op.size();k++) {
								
								//Getting each product details in the order
								JSONObject product = new JSONObject();
								product.put("fullfiller_name", name);
								product.put("fullfiller_id", cust_id);
								product.put("order_id", order_ids.get(j));
								
								product.put("quantity", op.get(k).getQuantity());
								product.put("price", op.get(k).getPrice());
								
								Catalog cat = cdao.getProductByID(op.get(k).getProduct_id());
								product.put("product_name", cat.getProduct_name());
								product.put("brand", cat.getBrand());
								product.put("description", cat.getDescription());
								product.put("pic_location", cat.getPic_location());
								order_details.put(product);
							}
							
							fullfiller_orders.put(order_details);
						}
						result.put(fullfiller_orders);
					}
				}
			}
			return result.toString();
		}
		return "fail";
	}
	
	// API to get all products bought for others(Manisha)
	@POST
	@Path("/getMyOrders/{id}")
	@Produces("application/json")
	public String getMyOrders(@PathParam("id") String fullfiller_id) throws JSONException {

		WishlistFullfillersDAO wfdao = new WishlistFullfillersDAO();
		WishlistDAO wd=new WishlistDAO();
		CustomerDAO cudao=new CustomerDAO();
		OrdersDAO wdao = new OrdersDAO();
		OrderProductDAO odao = new OrderProductDAO();
		CatalogDAO cdao = new CatalogDAO();
		
		List<WishlistFullfillers> wf_ids = wfdao.getIDsByFullfillerID(fullfiller_id);
		
		JSONArray result = new JSONArray();

		if (wf_ids != null) {
			for (int i = 0; i < wf_ids.size(); i++) {
				
				//Get creator details from wish list
				Wishlist wishlist=wd.getWishlistDetailsByID(wf_ids.get(i).getWishlist_id());
				String wishlist_name = wishlist.getName();
				String creator_id = wishlist.getCreator_id();
				Customer c=cudao.getCustomerByID(wishlist.getCreator_id());
				String name=c.getName();
				
				ArrayList<Integer> order_ids = wdao.getOrdersByID(wf_ids.get(i).getId());
				if (order_ids != null) {
					if(order_ids.size()!=0) {
						JSONArray my_orders = new JSONArray();
						for (int j = 0; j < order_ids.size(); j++) {
							JSONArray order_details = new JSONArray();
	
							List<OrderProduct> op = odao.getOrderDetailsByOrderID(order_ids.get(j));
							for(int k=0;k<op.size();k++) {
								JSONObject product = new JSONObject();
								product.put("order_id", order_ids.get(j));
								product.put("wishlist_name", wishlist_name);
								product.put("creator_name", name);
								product.put("creator_id", creator_id);
								
								product.put("quantity", op.get(k).getQuantity());
								product.put("price", op.get(k).getPrice());
								
								Catalog cat = cdao.getProductByID(op.get(k).getProduct_id());
								product.put("product_name", cat.getProduct_name());
								product.put("brand", cat.getBrand());
								product.put("description", cat.getDescription());
								product.put("pic_location", cat.getPic_location());
								order_details.put(product);
							}
							
							my_orders.put(order_details);
						}
						result.put(my_orders);
					}
				}
			}
			return result.toString();
		}
		return "fail";
	}
}
