<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
table{
	width:100%;
}
</style>
</head>
<body>
	<%
	ArrayList<ArrayList<String>> list= (ArrayList<ArrayList<String>>)session.getAttribute("books");
	
	out.print("<table ><tr><th>S.No.</th><th>Subject</th><th>Author Name</th><th>Price</th><th>Edition</th><th>Status</th></tr>");	
	
	for(ArrayList<String> l:list)
	{
		out.println("<tr>");
		for(String s:l)
		{
			out.print("<td>"+s+"</td>");
			
		}
		out.println("</tr>");
	}
	out.println("</table>");
	%>



<a href="add">click here to add more books</a>
	
	
	
	
</body>
</html>