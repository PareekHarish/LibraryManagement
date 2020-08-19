<head>
<title>Creative Colorlib SignUp Form</title>
<link href="css/style1.css" rel="stylesheet" type="text/css" media="all" />
<link href="//fonts.googleapis.com/css?family=Roboto:300,300i,400,400i,700,700i" rel="stylesheet">
</head>
<body>
<marquee>MBM Engineering College Jodhpur</marquee>
<div class="login-page">
<div class="harish">
<h2 class="pareek">Student Login</h2>
  <div class="form">
    <form class="login" action="loginstudent" method="post">
      <input type="email" name="email" placeholder="userId"/>
      <input type="password" name="password" placeholder="password"/>
      <button>login</button>
     </form>
      <p class="message">Not registered? <a href="signuppage">Create an account</a></p>
		<p class="message"><a href="forgetpassword">Forget Password</a></p>
 ${ pass }
${ userid }
${result }
  </div>
  </div>
  <div class="harish">
  <h2 class="pareek">Admin Login</h2>
   <div class="form">
    <form class="login" action="loginadmin" method="post">
      <input type="email" name="email" placeholder="userId"/>
      <input type="password" name="password" placeholder="password"/>
      <button>login</button>
     </form>
      <p class="message">Not registered? <a href="#">Create an account</a>></p>
      <p class="message"><a href="forgetpassword">Forget Password</a><p>
 ${ pass1 }
${ userid1 }
  </div>
</div>
</div>
</body>