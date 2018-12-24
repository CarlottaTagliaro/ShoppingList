<%-- 
    Document   : register
    Created on : 30-giu-2018, 18.07.07
    Author     : weatherly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <script src="JS/sweetalert2.all.min.js"/>

        <script>
            $(document).ready(function () {
                selectMenuEl("register");
            });
        </script>
        <c:if test="${not empty error_message}">

            <script>
                swal({
                    type: 'error',
                    title: 'Oops... Something went wrong!',
                    text: '${error_message}',
                    confirmButtonColor: '#222'
                })
            </script>
            <c:set var="error_message" value="" scope="session" />
        </c:if>
        <div class="main">
            <div class="card">
                <form action="RegisterServlet" method="POST">
                    <img class="logo spacing" src="images/carrello.png" alt="Insert sth" width="128" height="128">
                    <div class="form-group elemento spacing">
                        <label class="titles"> Name: </label>
                        <input type="name" id="nome" name="nome" class="form-control" placeholder="Enter name" required
                               autofocus>
                    </div>
                    <div class="form-group elemento spacing">
                        <label class="titles"> Surname: </label>
                        <input type="name" id="surname" name="surname" class="form-control" placeholder="Enter surname"
                               required autofocus>
                    </div>
                    <div class="form-group elemento spacing">
                        <label> Email: </label>
                        <input type="email" id="username" name="username" class="form-control" placeholder="Enter email"
                               required autofocus>
                    </div>
                    <div class="form-group elemento spacing">
                        <label> Password: </label>
                        <input type="password" id="password" name="password" class="form-control" placeholder="Enter new password"
                               required>
                    </div>
                    <div class="spacing">
                        <input type="checkbox" name="Privacy" value="true" required="true"> <b>I accept the <a href="privacy.jsp">privacy normatives</a></b>
                    </div>
                    <button class="button1" type="submit"> <b> Register </b> </button>
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