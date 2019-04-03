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
import org.acms.WishlistService.dao.WishlistDAO;
import org.acms.WishlistService.dao.WishlistFullfillersDAO;
import org.acms.WishlistService.dao.WishlistProductDAO;

import org.acms.WishlistService.model.Catalog;
import org.acms.WishlistService.model.Wishlist;
import org.acms.WishlistService.model.WishlistFullfillers;
import org.acms.WishlistService.model.WishlistProduct;

//imports required to send email to fullfiller
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  

@Path("/creator")
public class CreatorServices {
	
	// API to get all the wishlists shared with the user. (Deepika)
	@POST
	@Path("/getCreatorWishlistDetails/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public String getCreatorWishlistDetails(@PathParam("id") int wishlist_id) throws JSONException{
		WishlistDAO wdao = new WishlistDAO();
		Wishlist wishlist = wdao.getWishlistDetailsByID(wishlist_id);
		String wishlist_name = wishlist.getName();
		
		JSONArray items = new JSONArray();
		
		WishlistProductDAO wpdao = new WishlistProductDAO();
		List<WishlistProduct> wproducts = wpdao.getWishlistProductsByWishlistID(wishlist_id);
		
		if(wproducts!=null) {
			for(int i=0;i<wproducts.size();i++) {
				JSONObject item = new JSONObject();
				
				item.put("wishlist_name", wishlist_name);
				item.put("id", wproducts.get(i).getId());
				//item.put("product_id", wproducts.get(i).getProduct_id());
				item.put("quantity", wproducts.get(i).getQuantity());
				item.put("address", wproducts.get(i).getAddress());
				item.put("reason", wproducts.get(i).getReason());
				
				CatalogDAO cdao = new CatalogDAO();
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
	@Path("/updateWishlist")
	@Consumes("application/json")
	public String updateWishlist(WishlistProduct wishlist_product){
		WishlistProductDAO dao = new WishlistProductDAO();
		
		if(wishlist_product.getQuantity()==0) {
			return dao.deleteWishlistProduct(wishlist_product.getId());
		}
		
		else {
			ArrayList<String> field_names = new ArrayList<>();
			if(wishlist_product.getQuantity()>0) {
				field_names.add("quantity");
			}
			
			if(wishlist_product.getAddress()!=null) {
				field_names.add("address");
			}
			
			if(wishlist_product.getReason()!=null) {
				field_names.add("reason");
			}
			
			return dao.updateWishlistProduct(wishlist_product, field_names);
		}
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
		
		product.setId(product.getId());
		product.setWishlist_id(product.getWishlist_id());
		product.setProduct_id(product.getProduct_id());
		product.setQuantity(product.getQuantity());
		product.setAddress(product.getAddress());
		product.setReason(product.getReason());
		
		WishlistProductDAO dao = new WishlistProductDAO();
		
		if(dao.createWishlistAddProduct(product)!=-1)
			return "success";
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
	public String addWishlistFullfillers(WishlistFullfillers fullfiller)
	{	
		
		fullfiller.setFullfiller_id("deepika");
		fullfiller.setWishlist_id(fullfiller.getWishlist_id());
		
		WishlistFullfillersDAO dao = new WishlistFullfillersDAO();

		//sending email to fullfiller to tell that a wishlist has been shared with him
	    String USER_NAME = "";  // Gmail user name (just the part before "@gmail.com")
        String PASSWORD = "";   // Gmail password (removed for now)

        String RECIPIENT = fullfiller.getFullfiller_id();
        
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject = "Shared Wishlist";
        String body = "<h3>Hello,</h3><h4>A wishlist has been shared with you. Kindly open your account and fullfill the wishes of your loved ones.</h4>";
        
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
	    props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
            	toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setContent(body,"text/html" );   //to send html formatted email

            Transport transport = session.getTransport("smtp");

            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
						
		if(dao.addWishlistFllfillers(fullfiller)!=-1)
			return "success";
		else
			return "fail";
			 
	}
	
}
