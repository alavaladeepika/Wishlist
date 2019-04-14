package org.acms.WishlistService.services;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import org.acms.WishlistService.model.Customer;
import org.acms.WishlistService.model.Login;

import org.acms.WishlistService.dao.CustomerDAO;
import org.acms.WishlistService.dao.LoginDAO;

@SuppressWarnings("unused")
@Path("/customer")
public class CustomerServices {
	
	// API for user to create an account for the Customer (Deepika)
	@POST
	@Path("/registerUser")
	@Consumes("application/json")
	public String registerUser(String user) throws JSONException{
		
		JSONObject json = new JSONObject(user);
		
		Login login = new Login();
		login.setLogin_id(json.getString("login_id"));
		login.setPassword(json.getString("password"));
		
		Customer customer = new Customer();
		customer.setEmail_id(json.getString("email_id"));
		customer.setName(json.getString("name"));
		customer.setPhone_no(json.getString("phone_no"));
		customer.setLogin_id(json.getString("login_id"));
		customer.setDob(json.getString("dob"));
		customer.setGender(json.getString("gender"));
		
		LoginDAO ldao = new LoginDAO();
		CustomerDAO cdao=new CustomerDAO();
		
		if(cdao.checkEmail(customer.getEmail_id())=="exists") {
			return "email";
		}
		
		if(cdao.checkPhoneNo(customer.getPhone_no())=="exists") {
			return "phone_no";
		}
		
		if(cdao.checkLoginID(customer.getLogin_id())=="exists") {
			return "login_id";
		}
		
		ldao.createCustomerLogin(login);
		
		return cdao.createCustomer(customer);
	}
	
	// API for Customer to authenticate (Manisha)
	@POST
	@Path("/authenticate")
	@Consumes("application/json")
	@Produces("application/json")
	public Login getUserByLoginID(Login user)
	{
		LoginDAO dao = new LoginDAO();
		Login user_data = dao.getUser(user);
		
		if(user_data != null)
		{
			if(!user.getPassword().equals(user_data.getPassword()))
				return user;
			else
				return user_data;
		}
		else
			return null;
	}

	
}
