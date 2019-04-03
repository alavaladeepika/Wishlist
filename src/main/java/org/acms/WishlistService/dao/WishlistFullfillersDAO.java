package org.acms.WishlistService.dao;

import java.util.ArrayList;
import java.util.List;

import org.acms.WishlistService.model.WishlistFullfillers;;

public class WishlistFullfillersDAO extends HibernateDAO<WishlistFullfillers> {
	
	String entity="WishlistFullfillers";

	//Get the wishlist ids of the fullfiller given the fullfiller id(Deepika)
	public ArrayList<Integer> getWishlistsByFullfillerID(String fullfiller_id){
		
		//System.out.print("......................."+fullfiller_id);
		List<WishlistFullfillers> fullfillers = super.findAll(entity, "fullfiller_id", fullfiller_id);
		
		if(fullfillers.size()>0) {
			
			int size = fullfillers.size();
			ArrayList<Integer> wishlists = new ArrayList<>(size);
			
			for(int i=0;i<size;i++) {
				wishlists.add(fullfillers.get(i).getWishlist_id());
			}
			
			return wishlists;
		}
		
		else {
			return null;
		}
	}
	
	//Add fullfillers of a wishlist(Vaishali)
    public int addWishlistFllfillers(WishlistFullfillers fullfiller){
		
	    try {
		
	    	return super.add(fullfiller);
	    }
	    catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
    }

}
