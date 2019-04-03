package org.acms.WishlistService.dao;

import java.util.List;

import org.acms.WishlistService.model.Catalog;

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
}
