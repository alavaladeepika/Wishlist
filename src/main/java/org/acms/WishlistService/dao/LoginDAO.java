package org.acms.WishlistService.dao;

import org.acms.WishlistService.model.Login;

public class LoginDAO extends HibernateDAO<Login> {
	
	String entity="Login";

	//Deepika
	public void createCustomerLogin(Login login) {
		super.addNew(login);
		return;
	}
	
	//Manisha
	public Login getUser(Login user){
		return super.find(entity, "login_id", user.getLogin_id());
	}
}
