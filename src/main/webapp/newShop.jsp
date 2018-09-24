<%-- 
    Document   : newShop
    Created on : 23-set-2018, 15.02.26
    Author     : weatherly
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="main">
            <div class="card">
                <img class="logo" alt="Qui ci sarà la feature di caricamento, più di una foto" width="128" height="128">
                <div class="form-group elemento">
                    <label> Name: </label>
                    <input type="text" id="name" name="name" class="form-control" placeholder="Enter list name" required autofocus>
                </div>
                <button class="button1" type="submit" >Create</button>
                <button class = "button1"type="submit"  onclick="goBack()">Annulla</button>
                <script>
                    function goBack() {
                        window.history.back();
                    } 
                </script> 
            </div>
        </div>
    </body>
</html>