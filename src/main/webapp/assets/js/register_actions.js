/**
 * Deepika
 */
jQuery(document).ready(function($){
	function removeWarnings(){
		$("#warning_name").hide();
		$("#warning_email").hide();
		$("#email_exists").hide();
		$("#warning_phn").hide();
		$("#phn_exists").hide();
		$("#warning_dob").hide();
		$("#warning_gender").hide();
		$("#login_exists").hide();
		$("#warning_login").hide();
		$("#warning_pwd").hide();
		$("#warning_repwd").hide();
		$("#wrong_repwd").hide();
		$("#warning_check").show();
	}
	
	removeWarnings();
	
	$("body").on("click","#logo_img",function(){
		window.location = "catalog.html";
	});
	
	$("body").on("click","#signin",function(){
		window.location = "login.html";
	});
	
	$('#check_tnc').click(function() {
        if ($("#check_tnc").is(':checked')) {
        	$("#warning_check").hide();
        	$('#submit').removeAttr('disabled');
        } 
        else {
        	$("#warning_check").show();
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
		isValid();
	});
	
	//Check if everything is non-empty
	function isValid(){
		var i = 0;
		if(!$("#name").val()){
			$("#name").focus();
			$("#warning_name").show();
			i+=1;
		}
		if(!$("#email").val() && $("#email").val().search('@')==-1){
			$("#email").focus();
			$("#warning_email").show();
			$("#email_exists").hide();
			i+=1;
		}
		if(!$("#phn_no").val() && $("#phn_no").val().length!=10){
			$("#phn_no").focus();
			$("#warning_phn").show();
			$("#phn_exists").hide();
			i+=1;
		}
		if(!$("#dob").val()){
			$("#dob").focus();
			$("#warning_dob").show();
			i+=1;
		}
		if(!getGender()){
			$("#radio1").focus();
			$("#radio2").focus();
			$("#warning_gender").show();
			i+=1;
		}
		if(!$("#login_id").val()){
			$("#login_id").focus();
			$("#warning_login").show();
			$("#login_exists").hide();
			i+=1;
		}
		if(!$("#pwd").val() && $("#pwd").val().length<4){
			$("#pwd").focus();
			$("#warning_pwd").show();
			i+=1;
		}
		if(!$("#re_pwd").val() && $("#re_pwd").val().length<4){
			$("#re_pwd").focus();
			$("#warning_repwd").show();
			$("#wrong_repwd").hide();
			i+=1;
		}
		if($("#pwd").val()!=$("#re_pwd").val()){
			$("#pwd").focus();
			$("#re_pwd").focus();
			$("#wrong_repwd").show();
			$("#warning_repwd").hide();
			i+=1;
		}
		if(i==0){
			validate();
		}
	}
	
	//Validate email, phone number, login id
	//If valid enter it to database
	function validate(){
		removeWarnings();
		$("#warning_check").hide();
		
		var name = $("#name").val();
		var email = $("#email").val();
		var phn_no = $("#phn_no").val();
		var dob = $("#dob").val();
		var gender = getGender();
		var login_id = $("#login_id").val();
		var pwd = $("#pwd").val();
		var re_pwd = $("#re_pwd").val();
		
		var customer = {
			"name" : name,
			"email_id" : email,
			"phone_no" : phn_no,
			"dob" : dob,
			"gender" : gender,
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
		
		//console.log(customer);
		var url = "http://localhost:8080/WishlistService/webapi/customer/registerUser";
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : url,
			data: JSON.stringify(customer),
			success: function(status) {
				if(status=="success"){
					//console.log(data);
					setCookie("login_id", login_id, 1);
					alert("Successfully registered!!");
					console.log(checkCookie("login_id"));
					window.location = "catalog.html";
				}
				else if(status == "login_id"){
					//alert("Login ID already exists, choose different one!");
					$("#login_id").focus();
					$("#login_exists").show();
				}
				else if(status=="email"){
					//alert("Email id already exists, choose different one!");
					$("#email").focus();
					$("#email_exists").show();
				}
				else if(status=="phone_no"){
					//alert("Phone Number already exists, choose different one!");
					$("#phn_no").focus();
					$("#phn_exists").show();
				}
				else{
					alert("Something went wrong!");
				}
			},
			error: function(data) {
				alert("Failed!");
			}
		});
	}
	
});