package com.webapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.model.WebappModel;
import com.webapp.service.WebappService;

// Author is Harish Kumar Pareek
@Controller
public class WebappController {
	@Autowired
	WebappService webappservice;
	@RequestMapping(value="/")
	String m1()
		{
			System.out.println("Harish Pareek");
			return "login";
		}
	
	@RequestMapping(value="/signuppage")
	String signuppage()
		{
			return "signup";
		}
	
	@RequestMapping(value="/signup")
	String insertInfo(HttpServletRequest req,HttpServletResponse res,WebappModel user) throws SQLException,ServletException
	{
		String s=webappservice.insertInfo(user);
		if(s==null)
		{
			req.setAttribute("data","already Registered");
			return "signup";
		}
		req.setAttribute("data","Successfully Signup");
		return "signup";
	}
	
	
	@RequestMapping(value="/loginpage")
	String loginpage()
	{
		return "login";
	}
	
	@RequestMapping(value="/loginstudent")
	String loginStudent(HttpServletRequest req,WebappModel user,HttpSession sess)
	{
		if(sess.getAttribute("email")==null)
		{	
			String s=webappservice.loginStudent(user,req);
			if(s=="incorrect password")
			req.setAttribute("pass", "incorrect password");
			else if(s=="incorrect userid")
			req.setAttribute("userid", "incorrect userid");
			else
			{
				
				return "homepage";
			}
			return "login";
		}
		else
			return "homepage";
		
	}
	
	@RequestMapping(value="/loginadmin")
	public String loginAdmin(HttpServletRequest req,WebappModel user,HttpSession sess)
	{
		if(sess.getAttribute("email")==null)
		{
			String s=webappservice.loginAdmin(user,req);
			if(s=="incorrect password")
			req.setAttribute("pass1", "incorrect password");
			else if(s=="incorrect userid")
			req.setAttribute("userid1", "incorrect userid");
			else
			return "homepageadmin";
			return "login";
		}
		return "homepage";
	}
	
	@RequestMapping(value="/forgetpassword")
	public String forgetpassword()
	{
		return "forget";
	}
	
	
	
	@RequestMapping(value="/sendmail")
	public String email(HttpServletRequest req,WebappModel user) throws AddressException, MessagingException
	{
		System.out.println("in sendmail");
		Properties p=new Properties();
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.EnableSSL.enable", "true");
		p.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.socketFactory.port", "465");
		Session session = Session.getInstance(p, new SimpleAuthenticator("pareekharish23@gmail.com","Hp524675@"));
		Message msg =new MimeMessage(session);
		msg.setFrom(new InternetAddress("pareekharish23@gmail.com"));
		msg.setRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
		msg.setSubject("OTP verification application");
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    if(webappservice.saveOtp(user,number)==0)
	    {
	    	req.setAttribute("value","your email is not registered");
	    	return "forget";
	    }
	    else
	    {
			msg.setContent("OTP is"+number,"text/html");
			Transport.send(msg);
			req.setAttribute("email",user.getEmail());
			return "enterotp";
	    }
	}
	
	
	@RequestMapping(value="enterotp")
	public String enterOtp(HttpServletRequest req,WebappModel user)
	{
		System.out.println("in enterotp");
		System.out.println(user.getOtp());
		System.out.println(user.getEmail());
		if((webappservice.enterOtp(user)).equals("wrongotp"))
		{
			req.setAttribute("value","wrong OTP");
			return "enterotp";
		}
		else if((webappservice.enterOtp(user)).equals("changepass"))
		{
			req.setAttribute("email",user.getEmail());
			return "changepass";
		}
		return "enterotp";
	}
	
	@RequestMapping(value="password")
	public String password(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		return "changepass";
	}
	
	@RequestMapping(value="username")
	public String username(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		return "changeusername";
	}
	
	@RequestMapping(value="email")
	public String email(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		return "changeemail";
	}
	
	@RequestMapping(value="mobilenumber")
	public String mobilenumber(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		return "changemobilenumber";
	}
	
	
	@RequestMapping(value="changepass")
	public String changePassword(HttpServletRequest req,WebappModel user,HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		if(webappservice.changePassword(user,sess)==0)
		{
			req.setAttribute("result","there is some error");
			return "changepass";
		}
		else
		{
			sess.invalidate();
			req.setAttribute("result","password changes successfully");
		}
		return "login";
	}
	
	
	@RequestMapping(value="changeusername")
	public String changeUserName(HttpServletRequest req,WebappModel user,HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		if(webappservice.changeUserName(user,sess)==0)
		{
			req.setAttribute("result","there is some error");
			return "changeusername";
		}
		else
		{
			sess.invalidate();
			req.setAttribute("result","UserName changes successfully");
		}
		return "login";
	}
	
	@RequestMapping(value="changeemail")
	public String changeEmail(HttpServletRequest req,WebappModel user,HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		int x=webappservice.changeEmail(user,sess);
		if(x==0)
		{
			req.setAttribute("result","there is some error");
			return "changeemail";
		}
		else if(x==10)
		{
			req.setAttribute("result","This user is already exsist");
			return "changeemail";
		}
		else
		{
			sess.invalidate();
			req.setAttribute("result","Email changes successfully");
		}
		return "login";
	}
	
	@RequestMapping(value="changemobile")
	public String changeMobile(HttpServletRequest req,WebappModel user,HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		if(webappservice.changeMobile(user,sess)==0)
		{
			req.setAttribute("result","there is some error");
			return "changemobile";
		}
		else
		{
			sess.invalidate();
			req.setAttribute("result","MobileNumber changes successfully");
		}
		return "login";
	}
	
	
	
	@RequestMapping(value="inputphoto")
	public String inputPhoto(WebappModel user,HttpSession sess) throws IOException 
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		String filepath=uploadPhotoOnServer(user.getUserfile());
		sess.setAttribute("photopath",filepath);
		webappservice.inputPhoto(sess);
		return "homepage";
	}
	
	
	private String uploadPhotoOnServer(MultipartFile filedata) throws IOException 
	{	
			String rootdirectory="C:\\Users\\acer\\eclipse-workspace\\webapp\\WebContent\\images";
			File f=new File(rootdirectory);
			String filename=filedata.getOriginalFilename();
			if(filename!=null && filename.length()>0)
			{
				String filepath=f.getCanonicalPath()+File.separator+filename;
				try(BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filepath)))
				{
					bos.write(filedata.getBytes());
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				return filepath;
			}
		return null;
	}
	
	@RequestMapping(value="logout")
	public String logout(HttpSession sess)
	{
		sess.invalidate();
		return "login";
	}
	
	
	@RequestMapping(value="add")
	public String Add(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		return "addbooks";
	}
	
	@RequestMapping(value="addbook")
	public String addBook(HttpSession sess,WebappModel user,HttpServletRequest req)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		int x=webappservice.addBook(user);
		if(x==0)
			req.setAttribute("result","There is some error");
		else
			req.setAttribute("result","Book Added Successfully");
		return "addbooks";
	}
	
	@RequestMapping(value="viewallbooks")
	public String viewAllBooks(HttpSession sess)
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		webappservice.viewAllBooks(sess);
		
		
		return "viewallbooks";
	}
	
	@RequestMapping(value="issue")
	public String issueBook(WebappModel user,HttpSession sess,HttpServletRequest req) throws SQLException
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		
		String s=webappservice.issueBook(user,sess);
		if(s.equals("alreadyissued"))
			req.setAttribute("result","book is already issued");
		else if(s.equals("not available"))
			req.setAttribute("result","This book is not available in library");
		else if(s.contentEquals("successfully"))
		{
			req.setAttribute("result","Book has been Successfully issued");
			webappservice.viewAllBooks(sess);
		}
		else
			req.setAttribute("result","There is some Error");
		return "viewallbooks";
	}
	
	
	@RequestMapping(value="viewissuedbooks")
	public String viewIssueBooks(HttpSession sess,HttpServletRequest req) throws SQLException
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		String s=webappservice.viewIssueBooks(sess);
		if(s.equals("nobookissued"))
		{
			req.setAttribute("result","NO Books Are Issued");
		}
		else 
			return "viewissuebooks";
		return "homepage";
	}

	
	@RequestMapping(value="withdraw")
	public String withdrawBook(HttpSession sess,HttpServletRequest req,WebappModel user) throws SQLException
	{
		if((sess.getAttribute("email"))==null)
		{
			return "login";
		}
		String s=webappservice.withdrawBook(sess,user);
		if(s.equals("notIssued"))
			req.setAttribute("result","This Book Is Not Issued");
		else
		{
			req.setAttribute("result","Book Has Been Successfully Withdraw");
			webappservice.viewIssueBooks(sess);
		}
		return "viewissuebooks";
	}
	
}
