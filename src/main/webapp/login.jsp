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
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("login"); 
            });   
        </script>
        
        <div class="main">
            <div class="card">
                <img class="logo" src="http://www.settenews.net/wp-content/uploads/2016/08/tmp_25920-Screenshot_2016-08-16-07-03-43-1-564085934.png" alt="Insert sth" width="128" height="128">
                <div class="form-group elemento">
                    <label> Username: </label>
                    <input type="email" id="username" name="username" class="form-control" placeholder="Enter username" required autofocus>
                </div>
                <div class="form-group elemento">
                    <label> Password: </label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Enter password" required>   
                </div>   
                <div class="spacing">
                    <input type="checkbox" name="rememberMe" value="true"> <b>Remember me</b>
                </div>
                <button type="submit" >Sign in</button>
                <button type="submit" onclick="goBack()">Annulla</button>
                <script>
                    function goBack() {
                        window.history.back();
                    } 
                </script>   
                <label> You don't have an account? </label> <a href="register.jsp"> <b>Register</b></a>
                <label> You forgot your password? </label> <a onClick="a();" style="cursor: pointer; cursor: hand;"> <b>Reset Password</b></a>
                <script> function a() {
                        alert("New password sent!")
                    }</script>
            </div>
        </div>
    </body>
</html>