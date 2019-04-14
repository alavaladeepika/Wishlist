package org.acms.WishlistService.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServices {
	
	//Vaishali
	public String sendEmail(String[] email, String subject, String body) {
		//sending email to fullfiller to tell that a wishlist has been shared with him
	    String USER_NAME = "";  // Gmail user name (just the part before "@gmail.com")
        String PASSWORD = "";   // Gmail password (removed for now)

        //String RECIPIENT = email;
        
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = email; // list of recipient email addresses
        
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
	    props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
            	toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setContent(body,"text/html" );   //to send html formatted email

            Transport transport = session.getTransport("smtp");

            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
            return "success";
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
        return "fail";
	}
}
