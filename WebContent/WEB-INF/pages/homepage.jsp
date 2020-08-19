<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<h3>UserName  :${ sessionScope.username }</h3>
	<form action="username">
		<button>change UserName</button>
	</form><br>
	<h3>EmailId  :${ sessionScope.email }</h3>
	<form action="email">
		<button>change Email</button>
	</form><br>
	<h3>MobileNumber  :${ sessionScope.mobilenumber }</h3>
	<form action="mobilenumber">
		<button>change MobileNumber</button>
	</form><br>
	<h3>Password  :${ sessionScope.password }</h3>
	<form action="password">
		<button>change password</button>
	</form><br><br>
	<h2>${ sessionScope.photopath}</h2>	<br><br>
	
	<c:choose>
	<c:when test="${ sessionScope.photopath.equals(null) }">
	<h4>in when</h4>
		<form action="inputphoto" method="post" enctype="multipart/form-data">
		<input type="file" name="userfile" placeholder="upload your photo"><br><br>
		<input type="submit">
		</form><br><br>
	</c:when>
	<c:otherwise>
		<img src=${ sessionScope.photopath} alt="Trulli" width="250" height="200">
		<form action="inputphoto" method="post" enctype="multipart/form-data">
		<input type="file" name="userfile" placeholder="change your photo"><br><br>
		<input type="submit">
		</form><br><br>
	</c:otherwise>
	</c:choose>
	
	<form action="viewallbooks">
	<button>click here to view All Books</button>
	</form><br>
	
	<form action="viewissuedbooks">
	<button>click here to view Issued Books</button>
	</form><br>
	${result }
	
	
	<form action="logout">
		<button>LogOut</button>
	</form>
</body>
</html>