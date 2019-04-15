package org.acms.WishlistService.dao;

import java.util.ArrayList;
import java.util.List;

import org.acms.WishlistService.model.Orders;
import org.acms.WishlistService.model.WishlistFullfillers;

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
    
	//Get the order ids of the fullfiller given the wishlist_fullfiller_id(Manisha)
	public ArrayList<Integer> getOrdersByID(Integer wishlistfullfiller_id){
		
		//System.out.print("......................."+fullfiller_id);
		List<Orders> order = super.findAll(entity, "wishlistfullfiller_id", wishlistfullfiller_id);
		
		if(order.size()>0) {
			
			int size = order.size();
			ArrayList<Integer> orders = new ArrayList<>(size);
			
			for(int i=0;i<size;i++) {
				orders.add(order.get(i).getOrder_id());
			}
			
			return orders;
		}
		
		else {
			return null;
		}
	}

}
