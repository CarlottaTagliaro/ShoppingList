<%-- 
    Document   : register
    Created on : 30-giu-2018, 18.07.07
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/register.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        
        <script>
            $(document).ready(function() {
                selectMenuEl("register"); 
            });   
        </script>
        
        <div class="main">
            <div class="card">
                <img class="logo spacing" src="http://www.settenews.net/wp-content/uploads/2016/08/tmp_25920-Screenshot_2016-08-16-07-03-43-1-564085934.png" alt="Insert sth" width="128" height="128">
                <div class="form-group elemento spacing">
                    <label class="titles"> Name: </label>
                    <input type="name" id="nome" name="nome" class="form-control" placeholder="Enter name" required autofocus>
                </div>
                <div class="form-group elemento spacing">
                    <label class="titles"> Surname: </label>
                    <input type="name" id="surname" name="surname" class="form-control" placeholder="Enter surname" required autofocus>
                </div>
                <div class="form-group elemento spacing">
                    <label> Email: </label>
                    <input type="email" id="username" name="username" class="form-control" placeholder="Enter new username" required autofocus>
                </div>
                <div class="form-group elemento spacing">
                    <label> Password: </label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="Enter new password" required>   
                </div>   
                <div class="spacing">
                    <input type="checkbox" name="Privacy" value="true"> <b>I accept the privacy normatives</b>
                </div>
                <button class="button1" type="submit" > <b> Register </b> </button>
                <button class="button1" type="submit"  onclick="goBack()"> <b> Cancel </b> </button>
                <script>
                    function goBack() {
                        window.history.back();
                    }
                </script>
            </div>
        </div>
    </body>
</html>
