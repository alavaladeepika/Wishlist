package org.acms.WishlistService.dao;

import java.util.List;

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
    
    //Get the order with product details given the order id(Manisha)
  	public List<OrderProduct> getOrderDetailsByOrderID(int id) {
  		return super.findAll(entity, "order_id", id);
  	}
    
}
