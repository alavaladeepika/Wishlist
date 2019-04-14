package org.acms.WishlistService.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.acms.WishlistService.dao.CatalogDAO;
import org.acms.WishlistService.dao.CustomerDAO;
import org.acms.WishlistService.dao.WishlistDAO;
import org.acms.WishlistService.dao.WishlistProductDAO;
import org.acms.WishlistService.dao.WishlistFullfillersDAO;
import org.acms.WishlistService.model.Catalog;
import org.acms.WishlistService.model.Customer;
import org.acms.WishlistService.model.Wishlist;
import org.acms.WishlistService.model.WishlistProduct;
import org.acms.WishlistService.model.WishlistFullfillers;


@SuppressWarnings("unused")
@Path("/fullfiller")
public class FullfillerServices {
	
	// API to get all the wishlists shared with the user(Deepika)
	@POST
	@Path("/getSharedWishlists/{id}")
	@Produces("application/json")
	public String getSharedWishlists(@PathParam("id") String fullfiller_id) throws JSONException{
		
		WishlistFullfillersDAO wfdao = new WishlistFullfillersDAO();
		ArrayList<Integer> wishlists = wfdao.getWishlistsByFullfillerID(fullfiller_id);
		JSONArray result = new JSONArray();
		
		if(wishlists!=null) {
			for(int i=0; i<wishlists.size();i++) {
				JSONObject details = new JSONObject();
				details.put("wishlist_id", wishlists.get(i));
				
				WishlistDAO wdao = new WishlistDAO();
				Wishlist wishlist = wdao.getWishlistDetailsByID(wishlists.get(i));
				
				details.put("wishlist_name", wishlist.getName());
				details.put("creator_id", wishlist.getCreator_id());
				
				CustomerDAO cdao = new CustomerDAO();
				Customer customer = cdao.getCustomerByID(wishlist.getCreator_id());
				details.put("creator_name", customer.getName());

				result.put(details);
			}
			
			return result.toString();
		}
		return null;
	}
	
	//To get the details of shared wishlist(Vaishali)
	@POST
	@Path("/getWishlist")
	@Consumes("application/json")
	@Produces("application/json")
	public String getWishlist(WishlistProduct user_data) {
		
		WishlistProductDAO  WishlistProductDao = new WishlistProductDAO();
		List<WishlistProduct> wishlistedProducts = WishlistProductDao.getWishlistProductsByWishlistID(user_data.getWishlist_id());
		
		WishlistDAO WishlistDao = new WishlistDAO();
		Wishlist wishlist = WishlistDao.getWishlistDetailsByID(user_data.getWishlist_id());
		
		JSONArray products = new JSONArray();
		
		for(int i=0; i<wishlistedProducts.size();i++)
		{
			JSONObject product = new JSONObject();
			try {
				
				product.put("wishlist_name", wishlist.getName());
				product.put("remaining_qty", wishlistedProducts.get(i).getRemaining_qty());
				product.put("address", wishlistedProducts.get(i).getAddress());
				product.put("reason", wishlistedProducts.get(i).getReason());
					
				CatalogDAO CatalogDao = new CatalogDAO();
				Catalog productDetails = CatalogDao.getProductByID(wishlistedProducts.get(i).getProduct_id());
				
				product.put("product_id", productDetails.getProduct_id());
				product.put("product_name", productDetails.getProduct_name());
				product.put("product_qty", productDetails.getQuantity());
				product.put("brand", productDetails.getBrand());
				product.put("description", productDetails.getDescription());
				product.put("price", productDetails.getPrice());
				product.put("pic_location", productDetails.getPic_location());
				
				products.put(product);
				
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return products.toString();
		
	}
	
}
