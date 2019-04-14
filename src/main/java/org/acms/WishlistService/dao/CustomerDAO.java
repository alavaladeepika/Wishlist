package org.acms.WishlistService.dao;

import org.acms.WishlistService.model.Customer;

public class CustomerDAO extends HibernateDAO<Customer> {
	
	String entity="Customer";

	//Get the Customer details given the login id (Deepika)
	public Customer getCustomerByID(String id) {
		return super.find(entity, "login_id", id);
	}
	
	//Get the login id given the email (Deepika)
	public String getCustomerIDByEmail(String email) {
		Customer customer = super.find(entity, "email_id", email);
		if(customer==null) {
			return null;
		}
		return customer.getLogin_id();
	}
	
	//Add new customer (Deepika)
	public String createCustomer(Customer customer) {
		if(super.add(customer)>=1) {
			return "success";
		}
		return "fail";
	}
	
	//Check if email already exists (Deepika)
	public String checkEmail(String email) {
		Customer customer = super.find(entity, "email_id", email);
		if(customer!=null) {
			return "exists";
		}
		return "success";
	}

	//Check if phone number already exists (Deepika)
	public String checkPhoneNo(String phone_no) {
		Customer customer = super.find(entity, "phone_no", phone_no);
		if(customer!=null) {
			return "exists";
		}
		return "success";
	}
		
	//Check if login_id already exists (Deepika)
	public String checkLoginID(String login_id) {
		Customer customer = super.find(entity, "login_id", login_id);
		if(customer!=null) {
			return "exists";
		}
		return "success";
	}
}
