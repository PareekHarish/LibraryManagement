package com.webapp.controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SimpleAuthenticator extends Authenticator {

	String email=null;
	String mailPassword=null;
	public SimpleAuthenticator(String email, String mailPassword) {
		this.email=email;
		this.mailPassword=mailPassword;
	}
	
	
	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(email,mailPassword);
		
	}

}
