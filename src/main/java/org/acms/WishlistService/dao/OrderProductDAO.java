package org.acms.WishlistService.dao;

import org.acms.WishlistService.model.OrderProduct;
import org.acms.WishlistService.model.Wishlist;

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
    
  //Get the order with product details given the order id(Manisha)
  	public OrderProduct getOrderDetailsByOrderID(int id) {
  		return super.find(entity, "order_id", id);
  	}
    
}
