/**
 * Deepika
 */
jQuery(document).ready(function($){
	var result = true;
	$('#check_tnc').click(function() {
        if ($("#check_tnc").is(':checked')) {
        	$('#submit').removeAttr('disabled');
        } 
        else {
        	$('#submit').attr('disabled', 'disabled');
        }
    });
	
	function getGender(){
	    if($("#radio1").is(':checked')){
	    	return "F";
	    }
	    else if($("#radio2").is(':checked')){
	    	return "M";
	    }
	    else if($("#radio3").is(':checked')){
	    	return "others";
	    }
	    else{
	    	return null;
	    }
	}
	
	$('#submit').click(function(){
		var name = $("#name").val();
		var email = $("#email").val();
		var phn_no = $("#phn_no").val();
		var dob = $("#dob").val();
		var gender = getGender();
		var address = $("#address").val();
		var login_id = $("#login_id").val();
		var pwd = $("#pwd").val();
		var re_pwd = $("#re_pwd").val();

		/*isValidName(name);
		console.log(result.toString());
		isValidEmail(email);
		console.log(result.toString());
		isValidPhoneNo(phn_no); 
		console.log(result.toString());
		isValidDOB(dob);
		console.log(result.toString());
		isValidGender(gender);
		console.log(result.toString());
		isValidAddress(address);
		console.log(result.toString());
		isValidLoginID(login_id);
		console.log(result.toString());
		isValidPwd(pwd, re_pwd);
		console.log(result.toString());*/
		
		var customer = {
			"name" : name,
			"email_id" : email,
			"phone_no" : phn_no,
			"dob" : dob,
			"gender" : gender,
			"address" : address,
			"login_id" : login_id,
			"password" : pwd,
		};
		
		var login = {
			"login_id" : login_id,
			"password" : pwd,
		};
		
		var req_data = {
			"login" : login,
			"customer" : customer,
		}
		
		console.log(customer);
		var url = "http://localhost:8080/WishlistService/webapi/customer/registerUser";
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : url,
			data: JSON.stringify(customer),
			success: function(status) {
				if(status=="success"){
					//console.log(data);
					alert("Successfully registered!!");
				}
				else if(status == "login_id"){
					alert("Login ID already exists, choose different one!");
				}
				else if(status=="email"){
					alert("Email id already exists, choose different one!");
				}
				else if(status=="phone_no"){
					alert("Phone Number already exists, choose different one!");
				}
				else{
					alert("Something went wrong!");
				}
			},
			error: function(data) {
				alert("Failed!");
			}
		});
		
	});
	
	function isValidName(name){
		if(!name){
			$("#name").focus();
			result = result && false;
		}
		else{
			result = result && true;
		}
	}
	
	function isValidEmail(email){
		if(!email){
			$("#email").focus();
			result = result && false;
		}
		else{
			var req_data = {
					"email" : email,
			};
			$.ajax({url:"http://localhost:8080/wishlist_service/webapi/myresource/checkEmail", type:"POST",
				data: JSON.stringify(req_data),
				dataType: "json",
			 	async: true,
			 	
				success: function(data) {

					if(data.status=="fail"){
						//console.log(data);
						alert("Email is already in use");
						$("#email").focus();
						changeResult(false);
					}
					else if(data.status=="success"){
						result = result && true;
					}
				},
				error: function(data) {
					alert("failed");
					result = result && false;
				}
			});
		}
	}
	
	function changeResult(b){
		result = b;
	}
	
	function isValidPhoneNo(phn_no){
		//console.log(phn_no);
		if(!phn_no){
			$("#phn_no").focus();
			result = result && false;
		}
		else{
			var req_data = {
				"phn_no" : phn_no,
			};
			//console.log(req_data);
			$.ajax({url:"http://localhost:8080/wishlist_service/webapi/myresource/checkPhoneNo", type:"POST",
				data: JSON.stringify(req_data),
				dataType: "json",
			 	async: true,
			 	
				success: function(data) {

					if(data.status=="fail"){
						//console.log(data);
						alert("Phone number is already in use");
						$("#phn_no").focus();
						result = result && false;
					}
					else if(data.status=="success"){
						result = result && true;
					}
				},
				error: function(data) {
					alert("failed");
					result = result && false;
				}
			});
		}
	}
	
	function isValidDOB(dob){
		if(!dob){
			$("#dob").focus();
			result = result && false;
		}
		else{
			result = result && true;
		}
	}
	
	function isValidGender(gender){
		if(!gender){
			$("#radio1").focus();
			$("#radio2").focus();
			result = result && false;
		}
		else{
			result = result && true;
		}
	}
	
	function isValidAddress(address){
		if(!address){
			$("#address").focus();
			result = result && false;
		}
		else{
			result = result && true;
		}
	}
	
	function isValidLoginID(login_id){
		if(!login_id){
			$("#login_id").focus();
			result = result && false;
		}
		else{
			var req_data = {
				"login_id" : login_id,
			};
			$.ajax({url:"http://localhost:8080/wishlist_service/webapi/myresource/checkUser", type:"POST",
				data: JSON.stringify(req_data),
				dataType: "json",
			 	async: true,
			 	
				success: function(data) {

					if(data.status=="fail"){
						//console.log(data);
						alert("Login id is already taken");
						$("#login_id").focus();
						result = result && false;
					}
					else if(data.status=="success"){
						result = result && true;
					}
				},
				error: function(data) {
					alert("failed");
					result = result && false;
				}
			});
		}
	}
	
	function isValidPwd(pwd, re_pwd){
		if(!pwd && pwd.length<4){
			$("#pwd").focus();
			result = result && false;
		}
		if(!re_pwd && re_pwd.length<4){
			$("#re_pwd").focus();
			result = result && false;
		}
		else if(pwd==re_pwd){
			result = result && true;
		}
		else{
			alert("Passwords doesnot match");
			$("#pwd").focus();
			$("#re_pwd").focus();
			result = result && false;
		}
	}
});