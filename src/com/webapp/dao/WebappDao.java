package com.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.webapp.model.WebappModel;
@Repository
public class WebappDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	public String insertInfo(WebappModel user) throws SQLException,ServletException
	{
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement  pst1=con.prepareStatement("select * from student where email=?");
			pst1.setString(1,user.getEmail());
			ResultSet rs= pst1.executeQuery();
			if(rs.next())
			return null;
			else
			{
				PreparedStatement pst=con.prepareStatement("insert into student (email,password,username,mobilenumber,otp) values(?,?,?,?,?)");
				pst.setString(1,user.getEmail());
				pst.setString(2, user.getPassword());
				pst.setString(3, user.getUserName());
				pst.setString(4, user.getMobileNumber());
				Random rnd = new Random();
			    int number = rnd.nextInt(999999);
			    pst.setInt(5, number);
				pst.executeUpdate();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "signup";
	}
	public String loginStudent(WebappModel user,HttpServletRequest req) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("select * from student where email=?");
			pst.setString(1, user.getEmail());
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				if((rs.getString("password")).contentEquals(user.getPassword()))
				{
					HttpSession sess=req.getSession();
					sess.setAttribute("username",rs.getString("username"));
					sess.setAttribute("password",rs.getString("password"));
					sess.setAttribute("mobilenumber",rs.getString("mobilenumber"));
					sess.setAttribute("email",rs.getString("email"));
					sess.setAttribute("photopath",rs.getString("photopath"));
					return "correct";
				}
				else
				{
					return "incorrect password";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "incorrect userid";
	}
	
	public String loginAdmin(WebappModel user,HttpServletRequest req) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("select * from admin where email=?");
			pst.setString(1, user.getEmail());
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				if((rs.getString("password")).contentEquals(user.getPassword()))
				{
					PreparedStatement pst1=con.prepareStatement("select * from books");
					ResultSet rs1=pst1.executeQuery();
					ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>();
					while(rs1.next())
					{
						ArrayList<String> l=new ArrayList<String>();
						l.add(rs1.getString(1));
						l.add(rs1.getString(2));
						l.add(rs1.getString(3));
						l.add(rs1.getString(4));
						l.add(rs1.getString(5));
						l.add(rs1.getString(6));
						list.add(l);					
					}
					HttpSession sess=req.getSession();
					sess.setAttribute("books",list);
					sess.setAttribute("password",rs.getString("password"));
					sess.setAttribute("email", rs.getString("email"));
					return "correct";
				}
				else
				{
					return "incorrect password";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "incorrect userid";
	}
	
	public int saveOtp(WebappModel user, int number) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("update student set otp=? where email=?");
			pst.setInt(1,number);
			pst.setString(2,user.getEmail());
			return (pst.executeUpdate());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
		
		
	}
	
	public String enterOtp(WebappModel user) {
		
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("select otp from student where email=?");
			pst.setString(1,user.getEmail());
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				if(Integer.valueOf(rs.getInt("otp")).equals(Integer.valueOf(Integer.parseInt(user.getOtp()))))
					return "changepass";
				else
					return "wrongotp";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "enterotp";
	}
	
	
	public int changePassword(WebappModel user,HttpSession sess) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("update student set password=? where email=?");
			pst.setString(1,user.getPassword());
			pst.setString(2,(String) sess.getAttribute("email"));
			return pst.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
		
	}
	
	
	public int changeUserName(WebappModel user, HttpSession sess) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("update student set username=? where email=?");
			pst.setString(1,user.getUserName());
			pst.setString(2,(String) sess.getAttribute("email"));
			return pst.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int changeEmail(WebappModel user, HttpSession sess) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst1=con.prepareStatement("select * from student where email=?");
			pst1.setString(1,user.getEmail());
			ResultSet rs=pst1.executeQuery();
			if(rs.next())
			{
				return 10;
			}
			PreparedStatement pst=con.prepareStatement("update student set email=? where email=?");
			pst.setString(1,user.getEmail());
			pst.setString(2,(String) sess.getAttribute("email"));
			int x=pst.executeUpdate();
			return x;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int changeMobile(WebappModel user, HttpSession sess) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("update student set mobilenumber=? where email=?");
			pst.setString(1,user.getMobileNumber());
			pst.setString(2,(String) sess.getAttribute("email"));
			return pst.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public void inputPhoto(HttpSession sess) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("update student set photopath=? where email=?");
			pst.setString(1,(String) sess.getAttribute("photopath"));
			pst.setString(2,(String) sess.getAttribute("email"));
			 pst.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public int addBook(WebappModel user) {
		int x=0;
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("insert into books (subject,authorname,price,edition,currentstatus) values(?,?,?,?,?)");
			pst.setString(1,user.getSubject());
			pst.setString(2, user.getAuthorname());
			pst.setString(3, user.getPrice());
			pst.setString(4, user.getEdition());
			pst.setString(5,"Not Issued");
			x=pst.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return x;
	}
	public void viewAllBooks(HttpSession sess) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst1=con.prepareStatement("select * from books");
			ResultSet rs1=pst1.executeQuery();
			ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>();
			while(rs1.next())
			{
				ArrayList<String> l=new ArrayList<String>();
				l.add(rs1.getString(1));
				l.add(rs1.getString(2));
				l.add(rs1.getString(3));
				l.add(rs1.getString(4));
				l.add(rs1.getString(5));
				l.add(rs1.getString(6));
				list.add(l);					
			}
			sess.setAttribute("books",list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public String issueBook(WebappModel user, HttpSession sess) throws SQLException {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("select * from books where subject=?");
			pst.setString(1,user.getSubject());
			ResultSet rs=pst.executeQuery();
			int x=-1;
			int flag=0;
			while(rs.next())
			{
				flag=1;
				if(rs.getString("currentstatus").equals("Not Issued"))
				{
					x=rs.getInt(1);
					break;
				}
			}
			if(flag==1 && x==-1)
			{
				return "alreadyissued";
			}
			if(flag==0 && x==-1)
			{
				return "not available";
			}
			
			PreparedStatement pst2=con.prepareStatement("update books set currentstatus=? where sno=?");
			pst2.setString(1,"Issued");
			pst2.setInt(2, x);
			pst2.executeUpdate();
			
			PreparedStatement pst1=con.prepareStatement("insert into issuebooks (email,bookno) values (?,?)");
			pst1.setString(1,(String) sess.getAttribute("email"));
			pst1.setInt(2,x);
			pst1.executeUpdate();
			return "successfully";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String viewIssueBooks(HttpSession sess) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			PreparedStatement pst=con.prepareStatement("select * from books INNER JOIN issuebooks On (books.sno=issuebooks.bookno && email=?)");
			pst.setString(1,(String) sess.getAttribute("email"));
			ResultSet rs=pst.executeQuery();
			ArrayList<ArrayList<String>> list=new ArrayList<ArrayList<String>>();
			int x=-1;
			while(rs.next())
			{
				x=0;
				ArrayList<String> l=new ArrayList<String>();
				l.add(rs.getString(2));
				l.add(rs.getString(3));
				l.add(rs.getString(4));
				l.add(rs.getString(5));
				list.add(l);					
			}
			sess.setAttribute("issuebooks",list);
			if(x==-1)
				return "nobookissued";
			sess.setAttribute("issuebooks",list);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "yes";
	}
	public String withdrawBook(HttpSession sess, WebappModel user) {
		try(Connection con=jdbcTemplate.getDataSource().getConnection())
		{
			
			PreparedStatement pst=con.prepareStatement("select * from issuebooks INNER JOIN books on issuebooks.bookno=books.sno where (books.subject=? && issuebooks.email=?)" );
			pst.setString(1,user.getSubject());
			pst.setString(2,(String) sess.getAttribute("email"));
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				int y=rs.getInt("bookno");
				PreparedStatement pst2=con.prepareStatement("delete issuebooks from issuebooks where bookno=?");
				pst2.setInt(1,y);
				pst2.executeUpdate();
				PreparedStatement pst1=con.prepareStatement("update books set currentstatus=? where sno=?");
				pst1.setString(1,"Not Issued");
				pst1.setInt(2,y);
				pst1.executeUpdate();
				return "successfully";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "notIssued";
	}
}
