package org.acms.WishlistService.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.acms.WishlistService.dao.CatalogDAO;
import org.acms.WishlistService.dao.LoginDAO;
import org.acms.WishlistService.model.Catalog;
import org.acms.WishlistService.model.Login;

@SuppressWarnings("unused")
@Path("/catalog")
public class CatalogServices {
	
	//Manisha
	@GET
	@Path("/getProducts")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Catalog> getProducts()
	{
		CatalogDAO dao = new CatalogDAO();
		List<Catalog> user_data = dao.getCatalog();
		
		if(user_data != null)
		{
			return user_data;
		}
		else
			return null;
	}

}
