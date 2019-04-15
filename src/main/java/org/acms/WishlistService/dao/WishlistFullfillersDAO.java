package org.acms.WishlistService.dao;

import java.util.ArrayList;
import java.util.List;

import org.acms.WishlistService.model.Customer;
import org.acms.WishlistService.model.Wishlist;
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
	
	//Get fullfiller emails given the wishlist_id(Deepika)
	public String[] getFullfillersByWishlistID(int wishlist_id){
		
		List<WishlistFullfillers> fullfillers = super.findAll(entity, "wishlist_id", wishlist_id);
		
		if(fullfillers.size()>0) {
			
			CustomerDAO cdao = new CustomerDAO();
			
			int size = fullfillers.size();
			String fullfiller_emails[] = new String[size];
			
			for(int i=0;i<size;i++) {
				
				Customer customer = cdao.getCustomerByID(fullfillers.get(i).getFullfiller_id());
				if(customer!=null) {
					fullfiller_emails[i]=customer.getEmail_id();
				}
			}
			
			return fullfiller_emails;
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
    
    //Get id when given the fullfiller_id and wishlist_id (Deepika)
    public int getIDByDetails(WishlistFullfillers data) {
    	WishlistFullfillers fullfiller = super.find(entity, "wishlist_id", data.getWishlist_id(), "fullfiller_id", data.getFullfiller_id());
    	
    	if(fullfiller!=null) {
    		return fullfiller.getId();
    	}
    	
    	return -1;
    }
    
  //Get details given the fullfiller id(Manisha)
  	public List<WishlistFullfillers> getIDsByFullfillerID(String fullfiller_id){
  		
  		return super.findAll(entity, "fullfiller_id", fullfiller_id);
  		
  	}

  //Get the details given the wishlist id(Manisha)
  	public List<WishlistFullfillers> getDetailsByWishlistID(int id) {
  		return super.findAll(entity, "wishlist_id", id);
  	}
}
