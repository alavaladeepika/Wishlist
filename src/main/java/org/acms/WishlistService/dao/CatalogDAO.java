package org.acms.WishlistService.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.acms.WishlistService.model.Catalog;
import org.acms.WishlistService.model.Customer;

public class CatalogDAO extends HibernateDAO<Catalog> {
	
	String entity="Catalog";

	//Deepika
	public Catalog getProductByID(int id) {
		return super.find(entity, "product_id", id);
	}
	
	//Manisha
	public List<Catalog> getCatalog(){
		List<Catalog> catalog = super.list(new Catalog());
		if(catalog.size()==0) {
			return null;
		}
		return catalog;
	}
	
	//Vaishali
	public int reduceQuantity(int product_id, int qty) {
		
		Catalog product = new Catalog();
		product = getProductByID(product_id);
		int initial_quantity = product.getQuantity();
		product.setQuantity(initial_quantity - qty);
		
		List<Field> fields = new ArrayList<Field>();
		Field quantity_field;
		try {
			quantity_field = product.getClass().getDeclaredField("quantity");
			quantity_field.setAccessible(true);
			fields.add(quantity_field);
			
			if(super.update(product, "product_id", product_id, fields)==1)
				return 1;
			
		} 
		catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	//Get the Catalog details given the product id (Manisha)
		public Catalog getCatalogByProductID(int id) {
			return super.find(entity, "product_id", id);
		}
}
