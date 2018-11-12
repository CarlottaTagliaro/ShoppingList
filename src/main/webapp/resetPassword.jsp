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
    <link href="css/register.css" rel="stylesheet" type="text/css" />
    <title>Shopping List - Register</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon"/>
</head>

<body>
    <jsp:include page="menu.jsp" />

    <script>
        $(document).ready(function () {
            selectMenuEl("register");
        });   
    </script>

    <div class="main">
        <div class="card">
            <form action="ResetPassword" method="POST">
                <img class="logo spacing" src="images/carrello.png" alt="Insert sth" width="128" height="128">
                
                <div class="form-group elemento spacing">
                    <label> Email: </label>
                    <input type="email" id="username" name="username" class="form-control" placeholder="Enter user email"
                        required autofocus>
                </div>                
                <button class="button1" type="submit"> <b>Reset password </b> </button>
                <button class="button1" onclick="goBack()"> <b> Cancel </b> </button>
            </form>
            <script>
                function goBack() {
                    window.history.back();
                }
            </script>
        </div>
    </div>
</body>
</html>