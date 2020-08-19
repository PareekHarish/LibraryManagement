package com.webapp.service;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.dao.WebappDao;
import com.webapp.model.WebappModel;
@Service
public class WebappService {

	@Autowired
	WebappDao webappdao;
	public String insertInfo(WebappModel user) throws SQLException,ServletException
	{
		String s=webappdao.insertInfo(user);
		if(s==null)
		{
			return null;
		}
		return "signup";
		
	}
	public String loginStudent(WebappModel user,HttpServletRequest req) {
		return webappdao.loginStudent(user,req);
		
	}
	public String loginAdmin(WebappModel user, HttpServletRequest req) {
		return webappdao.loginAdmin(user, req);
		
	}
	public int saveOtp(WebappModel user, int number) {
		return webappdao.saveOtp(user,number);
		
	}
	public String enterOtp(WebappModel user) {
		return webappdao.enterOtp(user);
		
	}
	public int changePassword(WebappModel user,HttpSession sess) {
		return webappdao.changePassword(user,sess);
		
	}
	
	public int changeUserName(WebappModel user, HttpSession sess) {
		return webappdao.changeUserName(user,sess);
	}
	
	
	public int changeEmail(WebappModel user, HttpSession sess) {
		return webappdao.changeEmail(user,sess);
	}
	
	public int changeMobile(WebappModel user, HttpSession sess) {
		return webappdao.changeMobile(user,sess);
	}
	
	public void inputPhoto(HttpSession sess) {
		webappdao.inputPhoto(sess);
		
	}
	public int addBook(WebappModel user) {
		return webappdao.addBook(user);
		
	}
	public void viewAllBooks(HttpSession sess) {
		webappdao.viewAllBooks(sess);
		
	}
	public String issueBook(WebappModel user, HttpSession sess) throws SQLException {
		return webappdao.issueBook(user,sess);
		
	}
	public String viewIssueBooks(HttpSession sess) {
		return webappdao.viewIssueBooks(sess);
		
	}
	public String withdrawBook(HttpSession sess, WebappModel user) {
		return webappdao.withdrawBook(sess,user);
		
	}
}
