package org.acms.WishlistService.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import org.acms.WishlistService.model.Wishlist;
import org.acms.WishlistService.model.WishlistFullfillers;
import org.acms.WishlistService.model.WishlistProduct;

@Path("/creator")
public class CreatorServices {
	
	// API to get all the wishlists shared with the user. (Deepika)
	@POST
	@Path("/getCreatorWishlistDetails/{id}")
	@Produces("application/json")
	public String getCreatorWishlistDetails(@PathParam("id") int wishlist_id) throws JSONException{
		WishlistDAO wdao = new WishlistDAO();
		Wishlist wishlist = wdao.getWishlistDetailsByID(wishlist_id);
		String wishlist_name = wishlist.getName();
		
		JSONArray items = new JSONArray();
		
		//Products in wishlist
		WishlistProductDAO wpdao = new WishlistProductDAO();
		List<WishlistProduct> wproducts = wpdao.getWishlistProductsByWishlistID(wishlist_id);
		
		CatalogDAO cdao = new CatalogDAO();
		
		if(wproducts!=null) {
			for(int i=0;i<wproducts.size();i++) {
				JSONObject item = new JSONObject();
				
				item.put("wishlist_name", wishlist_name);
				item.put("id", wproducts.get(i).getId());
				//item.put("product_id", wproducts.get(i).getProduct_id());
				item.put("quantity", wproducts.get(i).getQuantity());
				item.put("fullfilled_qty", wproducts.get(i).getQuantity()-wproducts.get(i).getRemaining_qty());
				item.put("address", wproducts.get(i).getAddress());
				item.put("reason", wproducts.get(i).getReason());
				
				Catalog product = cdao.getProductByID(wproducts.get(i).getProduct_id());
				
				item.put("product_name", product.getProduct_name());
				item.put("brand", product.getBrand());
				item.put("price", product.getPrice());
				item.put("pic_location", product.getPic_location());
				item.put("description", product.getDescription());
				item.put("prod_quantity", product.getQuantity());
				
				items.put(item);
			}
			return items.toString();
		}
		return null;
	}
	
	// API to update details in the wishlist of the creator (Deepika)
	@POST
	@Path("/updateWishlist/{id}")
	@Consumes("application/json")
	public String updateWishlist(@PathParam("id") String creator, WishlistProduct wishlist_product){
		WishlistProductDAO dao = new WishlistProductDAO();
		
		EmailServices email_serv = new EmailServices();
		ArrayList<String> field_names = new ArrayList<>();
		if(wishlist_product.getQuantity()>=0) {
			field_names.add("quantity");
			field_names.add("remaining_qty");
		}
		
		if(wishlist_product.getAddress()!=null) {
			field_names.add("address");
		}
		
		if(wishlist_product.getReason()!=null) {
			field_names.add("reason");
		}
		
		//For sending email to fullfillers
		if(dao.updateWishlistProduct(wishlist_product, field_names)=="success") {
			WishlistProduct curr = dao.getWishlistProductByID(wishlist_product.getId());
			if(curr!=null) {
				int wishlist_id = curr.getWishlist_id();
				
				WishlistFullfillersDAO wfdao = new WishlistFullfillersDAO();
				String[] recipients = wfdao.getFullfillersByWishlistID(wishlist_id);
				
				if(recipients.length>0) {
					String subject = "Updated Wish list : Wish list Service";
			        String body = "<h2>Hello Fullfiller,</h3>"
			        			+ "<h3>Hope you are doing well. A wish list shared by '"+creator+"' has been updated. Kindly open your account and fullfill the wishes of your loved ones.</h3>"
			        			+ "<h4>Yours faithfully,</h4>"
			        			+ "<h4>Wish list Service Team.</h4>";
			        
			        return email_serv.sendEmail(recipients, subject, body);
				}
			}
			return "success";
		}
			
		return "fail";
	}
	
	// API to delete the wishlist of the creator, change the status to 'INACTIVE' (Deepika)
	@POST
	@Path("/deleteWishlist")
	@Consumes("application/json")
	public String deleteWishlist(Wishlist wishlist) {
		WishlistDAO dao = new WishlistDAO();
		return dao.deleteWishlist(wishlist);
	}
	
	// API to get all wishlists of creator(Manisha/Vaishali)
	@POST
	@Path("/getAllWishlists")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Wishlist> getWishlists(Wishlist user_data){
		WishlistDAO dao = new WishlistDAO();
		List<Wishlist> wishlists = dao.getallWishlists(user_data);
		
		if(wishlists != null){
			return wishlists;
		}
		else
			return null;
	}

	//For sharedWishlist(Vaishali)
	@POST
	@Path("/updateWishlistAdd")
	@Consumes("application/json")
	public String updateWishlistAddProduct(WishlistProduct product) {
		
		WishlistProductDAO dao = new WishlistProductDAO();
		
		if(dao.createWishlistAddProduct(product)!=-1) {
			EmailServices email_serv = new EmailServices();
			WishlistFullfillersDAO wdao = new WishlistFullfillersDAO();
			
			String[] recipients = wdao.getFullfillersByWishlistID(product.getWishlist_id());
			if(recipients.length>0) {
				String subject = "New Item added to Wish list : Wish list Service";
		        String body = "<h2>Hello Fullfiller,</h3>"
		        			+ "<h3>Hope you are doing well. A new product has been added to the wish list. Kindly open your account and fullfill the wishes of your loved ones.</h3>"
		        			+ "<h4>Yours faithfully,</h4>"
		        			+ "<h4>Wish list Service Team.</h4>";
		        
		        return email_serv.sendEmail(recipients, subject, body);
			}
			return "success";
		}
			
		else
			return "fail";
	}
		
	//For sharedWishlist(Vaishali)
	@POST
	@Path("/createWishlistAddProduct")
	@Consumes("application/json")
	public String createWishlistAddProduct(String wishlistProduct) throws Exception
	{	
		JSONObject wishlist_info = new JSONObject(wishlistProduct);
		
		Wishlist wishlist = new Wishlist();
		
		//wishlist.setWishlist_id(wishlist_info.getInt("wishlist_id"));
		wishlist.setCreator_id(wishlist_info.getString("creator_id"));
		wishlist.setName(wishlist_info.getString("name"));
		wishlist.setStatus(wishlist_info.getString("status"));
		
		WishlistDAO dao = new WishlistDAO();
		int wishlist_id = dao.createWishlist(wishlist);
		
		if(wishlist_id!=-1) {
			
			WishlistProduct product = new WishlistProduct();
			
			//product.setId(wishlist_info.getInt("id"));
			product.setWishlist_id(wishlist_id);
			product.setProduct_id(wishlist_info.getInt("product_id"));
			product.setQuantity(wishlist_info.getInt("quantity"));
			product.setAddress(wishlist_info.getString("address"));
			product.setReason(wishlist_info.getString("reason"));
			
			WishlistProductDAO WishlistProductDao = new WishlistProductDAO();
			
			if(WishlistProductDao.createWishlistAddProduct(product)!=-1)
				return "success";
			else
				return "fail";
		}
		else
			return "fail";
			 
	}
		
	//For shareWishlist(Vaishali)
	@POST
	@Path("/addWishlistFullfillers")
	@Consumes("application/json")
	public String addWishlistFullfillers(String data) throws JSONException
	{	
		JSONObject fullfiller_data = new JSONObject(data);
		
		WishlistFullfillers fullfiller = new WishlistFullfillers();
		
		CustomerDAO custdao = new CustomerDAO();
		String fullfiller_id = custdao.getCustomerIDByEmail(fullfiller_data.getString("email"));
		
		if(fullfiller_id==null) {
			return "email_fail";
		}
		
		fullfiller.setFullfiller_id(fullfiller_id);
		fullfiller.setWishlist_id(fullfiller_data.getInt("wishlist_id"));
		
		WishlistFullfillersDAO dao = new WishlistFullfillersDAO();

		String subject = "Shared Wish list : Wish list Service";
        String body = "<h2>Hello "+fullfiller_id+",</h3>"
        			+ "<h3>Hope you are doing well. A wish list has been shared with you. Kindly open your account and fullfill the wishes of your loved ones.</h3>"
        			+ "<h4>Yours faithfully,</h4>"
        			+ "<h4>Wish list Service Team.</h4>";
        
        String[] email = { fullfiller_data.getString("email") };
        EmailServices email_serv = new EmailServices();
        if(dao.addWishlistFllfillers(fullfiller)!=-1) {
        	email_serv.sendEmail(email, subject, body);
    		return "success";
        }
						
		return "fail";
			 
	}
	
	
	// API to get details of orders placed for a wishlist. (Manisha)
		@POST
		@Path("/getOrders/{id}")
		@Produces("application/json")
		public String getOrders(@PathParam("id") int wishlist_id) throws JSONException{
			WishlistFullfillersDAO dao = new WishlistFullfillersDAO();
			List<WishlistFullfillers> wishlist_fullfillers = dao.getDetailsByWishlistID(wishlist_id);
			JSONArray items = new JSONArray();
			if(wishlist_fullfillers != null)
			{

				for (int i = 0; i < wishlist_fullfillers.size(); i++) {

					CustomerDAO cudao=new CustomerDAO();
					Customer c=cudao.getCustomerByID(wishlist_fullfillers.get(i).getFullfiller_id());
					String name=c.getName();
					OrdersDAO wdao = new OrdersDAO();
					ArrayList<Integer> orders = wdao.getOrdersByID(wishlist_fullfillers.get(i).getId());
					if (orders != null) {
						for (int j = 0; j < orders.size(); j++) {
							JSONObject details = new JSONObject();
							details.put("id",j );
							details.put("fullfiller_name", name);
							details.put("order_id", orders.get(j));

							OrderProductDAO odao = new OrderProductDAO();
							OrderProduct op = odao.getOrderDetailsByOrderID(orders.get(j));
							details.put("quantity", op.getQuantity());
							details.put("price", op.getPrice());
							CatalogDAO cdao = new CatalogDAO();
							Catalog cat = cdao.getCatalogByProductID(op.getProduct_id());
							details.put("product_name", cat.getProduct_name());
							details.put("brand", cat.getBrand());
							details.put("description", cat.getDescription());
							details.put("pic_location", cat.getPic_location());
							items.put(details);
						}
					}
				}
				return items.toString();
			
			}
			return null;
		}
}
