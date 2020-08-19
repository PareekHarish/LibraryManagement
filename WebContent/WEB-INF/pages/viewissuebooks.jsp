<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
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
	ArrayList<ArrayList<String>> list= (ArrayList<ArrayList<String>>)session.getAttribute("issuebooks");
	
	out.print("<table ><tr><th>Subject</th><th>Author Name</th><th>Price</th><th>Edition</th></tr>");	
	
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


<form action="withdraw">
<input type="text" name="subject" placeholder="Enter book name" ><br><br>
<button>withdraw a book</button>
</form>

${result }


</body>
</html>