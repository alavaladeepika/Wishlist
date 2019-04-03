package org.acms.WishlistService.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.acms.WishlistService.model.WishlistProduct;

public class WishlistProductDAO extends HibernateDAO<WishlistProduct> {
	
	String entity="WishlistProduct";

	//Get the wishlisted products given the wishlist id(Deepika/Vaishali)
	public List<WishlistProduct> getWishlistProductsByWishlistID(int id) {
		return super.findAll(entity, "wishlist_id", id);
	}
	
	//Delete the product in the wishlist with wishlist_id(Deepika)
	public String deleteWishlistProduct(int id) {
		int rows = super.deleteRow(entity, "id", id);
		
		if(rows==1) {
			return "success";
		}
		return "fail";
	}
	
	//Update the wishlisted products(Deepika)
	public String updateWishlistProduct(WishlistProduct wishlist_product, ArrayList<String> field_names) {
		List<Field> fields = new ArrayList<Field>();
		
		try {
			
			for(int i=0;i<field_names.size();i++) {
				Field field = wishlist_product.getClass().getDeclaredField(field_names.get(i));
				field.setAccessible(true);
				fields.add(field);
			}
			if(super.update(wishlist_product, "id", wishlist_product.getId(), fields)==1) {
				return "success";
			}
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		} 
		
		return "fail";
			
	}
	
	//Add product to a wishlist(Vaishali)
    public int createWishlistAddProduct(WishlistProduct product){
		
	    try {
		
	    	return super.add(product);
	    }
	    catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

}
