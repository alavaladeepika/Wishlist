package org.acms.WishlistService.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.acms.WishlistService.model.Wishlist;

public class WishlistDAO extends HibernateDAO<Wishlist> {
	
	String entity="Wishlist";

	//Get the wishlist details given the wishlist id(deepika/Vaishali)
	public Wishlist getWishlistDetailsByID(int id) {
		return super.find(entity, "wishlist_id", id);
	}
	
	//Delete the wishlist, changing the status to 'INACTIVE' (Deepika)
	public String deleteWishlist(Wishlist wishlist) {
		
		try {
			
			List<Field> fields = new ArrayList<Field>();
			Field status_field = wishlist.getClass().getDeclaredField("status");
			status_field.setAccessible(true);
			fields.add(status_field);
			if(super.update(wishlist, "wishlist_id", wishlist.getWishlist_id(), fields)==1) {
				return "success";
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}

		return "fail";
	}
	
	//Get all the wishlists of the creator given the creator id (Manisha)
	public List<Wishlist> getallWishlists(Wishlist wishlist){
		List<Wishlist> wishlists = super.findAll(entity, "creator_id", wishlist.getCreator_id());
		if(wishlists.size()==0) {
			return null;
		}
		return wishlists;
	}
	
	//creating a wishlist (Vaishali)
	public int createWishlist(Wishlist wishlist){
		
	    try {
	    	return super.add(wishlist);
	    }
	    catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
}
