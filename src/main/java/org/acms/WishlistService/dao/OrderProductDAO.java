package org.acms.WishlistService.dao;

import org.acms.WishlistService.model.OrderProduct;

public class OrderProductDAO extends HibernateDAO<OrderProduct>{

	String entity="OrderProduct";
	
	//Add Ordered Products (Vaishali)
    public int addOrderedProduct(OrderProduct product) {
    	
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
