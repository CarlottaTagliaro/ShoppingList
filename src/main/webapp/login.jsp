<%--
    Document   : login
    Created on : 25-giu-2018, 18.18.05
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" href="favicon.ico" type="image/x-icon"/>
        <title>Shopping List - Login</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>

        <script>
            $(document).ready(function() {
                selectMenuEl("login");
            });
            
            
            function goBack() {
                window.history.back();
            }

            function reset() {
                alert("New password sent!")
            }
        </script>

        <div class="main">
            <div class="card">
                <form action="LoginServlet" method="POST">
                    <img class="logo" src="images/carrello.png" alt="Insert sth" width="128" height="128">
                    <div class="form-group elemento">
                        <label> Email: </label>
                        <input type="email" id="username" name="username" class="form-control" placeholder="Enter email" required autofocus>
                    </div>
                    <div class="form-group elemento">
                        <label> Password: </label>
                        <input type="password" id="password" name="password" class="form-control" placeholder="Enter password" required>
                    </div>
                    <div class="spacing">
                        <input type="checkbox" name="rememberMe" value="true"> <b>Remember me</b>
                    </div>
                    <button class="button1" type="submit"> <b> Sign in </b> </button>
                    <button class="button1" onclick="goBack()"> <b> Cancel </b> </button>
                    <label> You don't have an account? </label> <a href="register.jsp"> <b> Register</b></a>
                    <label> You forgot your password? </label> <a onClick="reset();" style="cursor: pointer; cursor: hand;"> <b>Reset Password</b></a>
                </form>
            </div>
        </div>
    </body>
</html>
