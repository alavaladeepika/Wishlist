package org.acms.WishlistService.services;

import java.util.ArrayList;
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
import org.acms.WishlistService.dao.WishlistDAO;
import org.acms.WishlistService.dao.WishlistFullfillersDAO;
import org.acms.WishlistService.dao.WishlistProductDAO;

import org.acms.WishlistService.model.Catalog;
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
				
				if(recipients!=null) {
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
		if(dao.deleteWishlist(wishlist)=="success") {
			EmailServices email_serv = new EmailServices();
			
			//Get fullfiller emails
			WishlistFullfillersDAO wfdao = new WishlistFullfillersDAO();
			String[] fullfillers = wfdao.getFullfillersByWishlistID(wishlist.getWishlist_id());
			
			if(fullfillers!=null) {
				String subject = "Deleted Wish list : Wish list Service";
		        String body = "<h2>Hello,</h3>"
		        			+ "<h3>"
		        				+ "Hope you are doing well. The wish list named '"
		        				+ wishlist.getName() + "', shared by '"+wishlist.getCreator_id()+"', has been deleted. "
			        			+ "Kindly open your account and fullfill the wishes of your loved ones."
			        		+ "</h3>"
			        		+ "<h4>Yours faithfully,</h4>"
			        		+ "<h4>Wish list Service Team.</h4>";
			    return email_serv.sendEmail(fullfillers, subject, body);
			}
			return "success";
		}
		return "fail";
	}
	
	// API to get all wishlists of creator(Manisha)
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
	
	// API to get all wishlists of creator(Vaishali)
	@POST
	@Path("/getOngoingWishlists")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Wishlist> getOngoingWishlists(Wishlist user_data){
		WishlistDAO dao = new WishlistDAO();
		List<Wishlist> wishlists = dao.getOngoingWishlists(user_data);
		
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
		
		if(dao.checkProductExistsInWishlist(product.getWishlist_id(), product.getProduct_id())==0) {
			if(dao.createWishlistAddProduct(product)!=-1) {
				EmailServices email_serv = new EmailServices();
				WishlistFullfillersDAO wdao = new WishlistFullfillersDAO();
				
				String[] recipients = wdao.getFullfillersByWishlistID(product.getWishlist_id());
				if(recipients!=null) {
					String subject = "New Item added to Wish list : Wish list Service";
			        String body = "<h2>Hello Fullfiller,</h3>"
			        			+ "<h3>Hope you are doing well. A new product has been added to a wish list shared with you. Kindly open your account and fullfill the wishes of your loved ones.</h3>"
			        			+ "<h4>Yours faithfully,</h4>"
			        			+ "<h4>Wish list Service Team.</h4>";
			        
			        return email_serv.sendEmail(recipients, subject, body);
				}
				return "success";
			}
				
			else
				return "fail";
		}
		else {
			return "ProductAlreadyExists";
		}
	}
		
	//For sharedWishlist(Vaishali)
	@POST
	@Path("/createWishlistAddProduct")
	@Consumes("application/json")
	public String createWishlistAddProduct(String wishlistProduct) throws Exception
	{	
		JSONObject wishlist_info = new JSONObject(wishlistProduct);
		
		Wishlist wishlist = new Wishlist();
		
		wishlist.setCreator_id(wishlist_info.getString("creator_id"));
		wishlist.setName(wishlist_info.getString("name"));
		wishlist.setStatus(wishlist_info.getString("status"));
		
		WishlistDAO dao = new WishlistDAO();
		int wishlist_id = dao.createWishlist(wishlist);
		
		if(wishlist_id!=-1) {
			
			WishlistProduct product = new WishlistProduct();
			
			product.setWishlist_id(wishlist_id);
			product.setProduct_id(wishlist_info.getInt("product_id"));
			product.setQuantity(wishlist_info.getInt("quantity"));
			product.setRemaining_qty(wishlist_info.getInt("remaining_qty"));
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
		WishlistFullfillersDAO fullfillerDao = new WishlistFullfillersDAO();
		
		CustomerDAO custdao = new CustomerDAO();
		String fullfiller_id = custdao.getCustomerIDByEmail(fullfiller_data.getString("email"));
		
		if(fullfiller_id==null) {
			return "email_fail";
		}
		
		if(fullfillerDao.checkIfFullfillerAlreadyExists(fullfiller_data.getInt("wishlist_id"), fullfiller_data.getString("email"))==1) {
			return "FullfillerAlreadyExists";
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
	
}
