package org.acms.WishlistService.dao;

import org.acms.WishlistService.model.Orders;

public class OrdersDAO extends HibernateDAO<Orders>{
	
	String entity="Orders";
	
	//Add orders (Vaishali)
    public int addOrders(Orders order){
		
	    try {
		
	    	return super.add(order);
	    }
	    catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
    }

}
